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
import com.chillin.hearting.oauth.domain.PrincipalDetails;
import com.chillin.hearting.oauth.domain.ProviderType;
import com.chillin.hearting.oauth.service.OAuth2Attribute;
import com.chillin.hearting.util.CookieUtil;
import com.chillin.hearting.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private static final String ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final Environment environment;


    @Value("${app.auth.refresh-token-expiry}")
    private long refreshTokenExpiry;


    @Transactional
    public SocialLoginData kakaoLogin(String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException {

        String kakaoAccessToken = getKakaoAccessToken(code);

        SocialLoginData socialLoginData = null;

        try {
            User kakaoUser = getKakaoInfo(kakaoAccessToken);
            log.debug("{}", kakaoUser);

            if (kakaoUser == null) {
                throw new NotFoundException("카카오로부터 user 정보를 가져오지 못했습니다.");
            }

            AuthToken accessToken = makeAccessToken(kakaoUser.getId());

            AuthToken refreshToken = makeRefreshToken();

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

        } catch (IllegalArgumentException e) {
            log.error("로그인 실패 : {}", e.getMessage());
            throw new IllegalArgumentException("카카오로부터 user 정보를 가져오지 못했습니다.");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        return socialLoginData;

    }

    // 카카오에서 access token 받아오기
    public String getKakaoAccessToken(String code) {

        String kakaoAccessToken = "";
        try {
            String tokenUri = environment.getProperty("spring.security.oauth2.client.provider." + "kakao" + ".token-uri");
            URL url = new URL(tokenUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String clientId = environment.getProperty("spring.security.oauth2.client.registration." + "kakao" + ".client-id");
            String clientSecret = environment.getProperty("spring.security.oauth2.client.registration." + "kakao" + ".client-secret");
            String redirectUri = environment.getProperty("spring.security.oauth2.client.registration." + "kakao" + ".redirect-uri");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + clientId);
            sb.append("&client_secret=" + clientSecret);
            sb.append("&redirect_uri=" + redirectUri);
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
            String userInfoUri = environment.getProperty("spring.security.oauth2.client.provider." + "kakao" + ".user-info-uri");
            URL url = new URL(userInfoUri);
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

            OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("kakao", (Map<String, Object>) jsonObject);

//            Map<String, Object> kakaoAccount = (Map<String, Object>) jsonObject.get("kakao_account");

//            String email = (String) kakaoAccount.get("email");
            log.debug("oauth2attribute test : {}", oAuth2Attribute.getAttributes());
            log.debug("kakao에 등록된 이메일 : {}", oAuth2Attribute.getEmail());

            user = userRepository.findByEmail(oAuth2Attribute.getEmail()).orElse(null);

            if (user != null) {
                log.debug("카카오로 로그인을 한 적이 있는 user입니다.");

                // 계정 일시 정지인 경우
                if (user.getStatus() == 'P') {
                    BlockedUser blockedUser = blockedUserRepository.findByUserId(user.getId()).orElseThrow(UserNotFoundException::new);
                    LocalDateTime locaDateTimeNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

                    // 계정 정지 기간이 이미 기간이 지난 경우
                    if (blockedUser.getEndDate().isBefore(locaDateTimeNow)) {
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
                user = User.builder().id(uuid.toString()).type(ProviderType.KAKAO.toString()).email(oAuth2Attribute.getEmail()).nickname(nickname).build();
                return userRepository.saveAndFlush(user);
            }
        } catch (UnAuthorizedException e) {
            log.error("로그인 한 회원 status : {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        } catch (IOException | ParseException e) {
            log.error(e.getMessage());
        }
        return user;
    }

    @Transactional
    public UpdateNicknameData updateNickname(String userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.updateNickname(nickname);

        return UpdateNicknameData.builder()
                .nickname(user.getNickname())
                .build();

    }

    @Transactional
    public UpdateStatusMessageData updateStatusMessage(String userId, String statusMessage) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.updateStatusMessage(statusMessage);

        return UpdateStatusMessageData.builder()
                .statusMessage(user.getStatusMessage())
                .build();

    }

    @Transactional
    public void deleteRefreshToken(String userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.deleteRefreshToken();

        userRepository.save(user);

        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);

    }

    // 하트판 주인 정보 조회
    public HeartBoardOwnerData getBoardOwnerInformation(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return HeartBoardOwnerData.builder()
                .nickname(user.getNickname())
                .statusMessage(user.getStatusMessage())
                .messageTotal(user.getMessageTotal())
                .build();
    }

    // access token 재발급
    @Transactional
    public ReissuedAccessTokenData reissueAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String headerAccessToken = HeaderUtil.getAccessToken(httpServletRequest);

        AuthToken authHeaderAccessToken = tokenProvider.convertAuthToken(headerAccessToken);
        Authentication authentication = tokenProvider.getExpiredUser(authHeaderAccessToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

//        User user = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(UserNotFoundException::new);
        User user = principalDetails.getUser();
        String refreshToken = CookieUtil.getCookie(httpServletRequest, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null || !refreshToken.equals(user.getRefreshToken())) {
            deleteRefreshToken(user.getId(), httpServletRequest, httpServletResponse);
            throw new UnAuthorizedException("DB에 저장되어 있는 refreshToken과 다릅니다. 다시 로그인 해주세요.");
        }

        log.debug("쿠키에 담긴 refreshToken : {}", refreshToken);

        AuthToken authTokenRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        Claims refreshClaims = authTokenRefreshToken.getExpiredTokenClaims();

        long expirationTime = refreshClaims.get("exp", Long.class); // "exp" 필드 값을 가져옵니다.
        long currentTime = System.currentTimeMillis() / 1000; // 현재 시간을 초 단위로 가져옵니다.

        if (expirationTime < currentTime || user.getRefreshToken() == null) {
            log.debug("유효하지 않은 refresh token 입니다.");
            deleteRefreshToken(user.getId(), httpServletRequest, httpServletResponse);

            throw new UnAuthorizedException("유효하지 않은 refresh token 입니다.");
        }


        AuthToken accessToken = makeAccessToken(user.getId());

        log.debug("정상적으로 액세스토큰 재발급!!!");


        return ReissuedAccessTokenData.builder().accessToken(accessToken.getToken()).build();
    }

    public AuthToken makeAccessToken(String userId) {

        Date now = new Date();

        return tokenProvider.createAuthToken(
                userId,
                ROLE,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );
    }

    public AuthToken makeRefreshToken() {

        Date now = new Date();

        return tokenProvider.createAuthToken(new Date(now.getTime() + refreshTokenExpiry));
    }


}
