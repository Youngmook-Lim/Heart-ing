package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.*;
import com.chillin.hearting.db.domain.BlockedUser;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.BlockedUserRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.NotFoundException;
import com.chillin.hearting.exception.UnAuthorizedException;
import com.chillin.hearting.exception.UserNotFoundException;
import com.chillin.hearting.jwt.AuthToken;
import com.chillin.hearting.jwt.AuthTokenProvider;
import com.chillin.hearting.oauth.domain.AppProperties;
import com.chillin.hearting.oauth.domain.ProviderType;
import com.chillin.hearting.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String SUCCESS = "success";
    private static final String REFRESH_TOKEN = "refreshToken";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAKAO_TOKEN_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String KAKO_USER_INFO_URI;

    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Transactional
    public SocialLoginData kakaoLogin(String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException {

        String kakaoAccessToken = getKakaoAccessToken(code);

        HttpStatus status = null;
        SocialLoginData socialLoginData = null;

        try {
            User kakaoUser = getKakaoInfo(kakaoAccessToken);
            log.debug("{}", kakaoUser);

            if (kakaoUser == null) {
                throw new NotFoundException("카카오로부터 user 정보를 가져오지 못했습니다.");
            }

            Date now = new Date();

            AuthToken accessToken = tokenProvider.createAuthToken(
                    kakaoUser.getId(),
                    "ROLE_USER",
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
            );

            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            AuthToken refreshToken = tokenProvider.createAuthToken(
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            log.debug("accessToken : {}", accessToken.getToken());
            log.debug("refreshToken : {}", refreshToken.getToken());

            kakaoUser.saveRefreshToken(refreshToken.getToken());
            log.debug("kakaoUser 리프레시 토큰 저장한 후 : {}", kakaoUser.getRefreshToken());
            userRepository.saveAndFlush(kakaoUser);

            socialLoginData = SocialLoginData.builder()
                    .userId(kakaoUser.getId())
                    .nickname(kakaoUser.getNickname())
                    .accessToken(accessToken.getToken())
                    .build();

            int cookieMaxAge = (int) refreshTokenExpiry / 60;

            CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);
            CookieUtil.addCookie(httpServletResponse, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
            status = HttpStatus.ACCEPTED;

        } catch (IllegalArgumentException e) {
            log.error("로그인 실패 : {}", e.getMessage());
            throw new IllegalArgumentException("카카오로부터 user 정보를 가져오지 못했습니다.");
        }
        return socialLoginData;

    }

    // 카카오에서 access token 받아오기
    public String getKakaoAccessToken(String code) {

        String kakaoAccessToken = "";
        try {
            URL url = new URL(KAKAO_TOKEN_URI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + KAKAO_CLIENT_ID);
            sb.append("&client_secret=" + KAKAO_CLIENT_SECRET);
            sb.append("&redirect_uri=" + KAKAO_REDIRECT_URI);
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.debug("카카오에서 access token 받아오기 response code : {}  ", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder kakaoResponse = new StringBuilder();

            while ((line = br.readLine()) != null) {
                kakaoResponse.append(line);
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(kakaoResponse.toString());

            kakaoAccessToken = (String) jsonObject.get("access_token");

            br.close();
            bw.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return kakaoAccessToken;
    }

    // 카카오에서 회원정보 받아오기
    @Transactional
    public User getKakaoInfo(String kakaoAccessToken) {
        User user = null;

        try {
            URL url = new URL(KAKO_USER_INFO_URI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);
            int responseCode = conn.getResponseCode();
            log.debug("responseCode : {} ", responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder kakaoResponse = new StringBuilder();

            while ((line = br.readLine()) != null) {
                kakaoResponse.append(line);
            }

            log.debug("카카오에서 사용자 정보 가져오기 response body : {} ", kakaoResponse);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(kakaoResponse.toString());

            Map<String, Object> kakaoAccount = (Map<String, Object>) jsonObject.get("kakao_account");

            String email = (String) kakaoAccount.get("email");
            log.debug("kakao에 등록된 이메일 : {}", email);

            user = userRepository.findByEmail(email).orElse(null);

            if (user != null) {
                log.debug("카카오로 로그인을 한 적이 있는 user입니다.");

                // 계정 일시 정지인 경우
                if (user.getStatus() == 'P') {
                    BlockedUser blockedUser = blockedUserRepository.findByUserId(user.getId()).orElseThrow(UserNotFoundException::new);
                    LocalDateTime localDateTime = LocalDateTime.now();

                    // 계정 정지 기간이 이미 기간이 지난 경우
                    if (!localDateTime.isBefore(blockedUser.getEndDate())) {
                        user.updateUserStatusToActive();
                        log.debug("계정 일시 정지 풀고 난 후 user status : {}", user.getStatus());
                        return userRepository.saveAndFlush(user);
                    }

                    throw new UnAuthorizedException("pause");
                }
                // 계정 영구 정지인 경우
                else if (user.getStatus() == 'O') {
                    throw new UnAuthorizedException("out");
                }
            } else {
                log.debug("카카오 로그인 최초입니다.");

                String nickname = "";

                UUID uuid = UUID.randomUUID();
                user = User.builder().id(uuid.toString()).type(ProviderType.KAKAO.toString()).email(email).nickname(nickname).build();
                return userRepository.saveAndFlush(user);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return user;
    }

    @Transactional
    public UpdateNicknameData updateNickname(String userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.updateNickname(nickname);

        UpdateNicknameData updateNicknameData = UpdateNicknameData.builder().nickname(user.getNickname()).build();

        return updateNicknameData;

    }

    @Transactional
    public UpdateStatusMessageData updateStatusMessage(String userId, String statusMessage) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.updateStatusMessage(statusMessage);

        UpdateStatusMessageData updateStatusMessageData = UpdateStatusMessageData.builder().statusMessage(user.getStatusMessage()).build();

        return updateStatusMessageData;

    }

    @Transactional
    public void deleteRefreshToken(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.deleteRefreshToken();

    }

    // 하트판 주인 정보 조회
    public HeartBoardOwnerData getBoardOwnerInformation(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        HeartBoardOwnerData heartBoardOwnerData = HeartBoardOwnerData.builder()
                .nickname(user.getNickname())
                .statusMessage(user.getStatusMessage())
                .messageTotal(user.getMessageTotal())
                .build();

        return heartBoardOwnerData;
    }

    // access token 재발급
    public ReissuedAccessTokenData reissueAccessToken(String userId, HttpServletRequest httpServletRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String refreshToken = CookieUtil.getCookie(httpServletRequest, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        log.debug("쿠키에 담긴 refreshToken : {}", refreshToken);

        AuthToken authTokenRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authTokenRefreshToken.validate() || user.getRefreshToken() == null) {
            log.debug("유효하지 않은 refresh token 입니다.");
            throw new UnAuthorizedException("유효하지 않은 refresh token 입니다.");
        }

        Date now = new Date();


        AuthToken accessToken = tokenProvider.createAuthToken(
                user.getId(),
                "ROLE_USER",
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        log.debug("정상적으로 액세스토큰 재발급!!!");

        ReissuedAccessTokenData reissuedAccessTokenData = ReissuedAccessTokenData.builder().accessToken(accessToken.getToken()).build();

        return reissuedAccessTokenData;
    }


}
