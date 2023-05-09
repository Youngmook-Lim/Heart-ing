package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.SocialLoginData;
import com.chillin.hearting.api.data.SocialLoginResultData;
import com.chillin.hearting.db.domain.BlockedUser;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.BlockedUserRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.NotFoundException;
import com.chillin.hearting.exception.UnAuthorizedException;
import com.chillin.hearting.exception.UserNotFoundException;
import com.chillin.hearting.jwt.AuthToken;
import com.chillin.hearting.oauth.info.OAuth2Attribute;
import com.chillin.hearting.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String CLIENT_PROVIDER = "spring.security.oauth2.client.provider.";
    private static final String CLIENT_REGISTRATION = "spring.security.oauth2.client.registration.";

    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final Environment environment;
    private final UserService userService;
    private final MigrationService migrationService;
    private final MessageService messageService;


    @Value("${app.auth.refresh-token-expiry}")
    private long refreshTokenExpiry;


    @Transactional
    public SocialLoginData socialLogin(String code, String provider, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException {

        String socialAccessToken = getSocialAccessToken(code, provider);

        SocialLoginData socialLoginData = null;

        try {
            SocialLoginResultData socialLoginResultData = getSocialUserInfo(socialAccessToken, provider);
            log.info("getSocialUserInfo 리턴값 : {}", socialLoginResultData);

            User socialUser = socialLoginResultData.getUser();

            if (socialUser == null) {
                throw new NotFoundException(provider + "로부터 user 정보를 가져오지 못했습니다.");
            }

            if (socialLoginResultData.isFirst()) {

                // 레디스에 유저 보낸 하트 정보 등록
                migrationService.migrateUserSentHeart(socialUser.getId());

                messageService.sendMessage(7L, "SUPER_USER", socialUser.getId(), "환영합니다!\uD83D\uDC95", "안녕하세요!( >ᴗ< )\n" +
                        "하팅 개발진의 감사한 마음을 \n" +
                        "모두 모아 첫 번째 하트를 \n" +
                        "보냅니다.❤\uFE0F\uD83D\uDC9B\uD83D\uDC9A\uD83D\uDC99\uD83D\uDC9C\n" +
                        "하트에 전달하고 싶은 마음, \n" +
                        "감정을 담아 주고받아 보세요.\n" +
                        "하팅!", "");
            }

            AuthToken accessToken = userService.makeAccessToken(socialUser.getId());

            AuthToken refreshToken = userService.makeRefreshToken();

            log.debug("accessToken : {}", accessToken.getToken());
            log.debug("refreshToken : {}", refreshToken.getToken());

            socialUser.saveRefreshToken(refreshToken.getToken());
            log.debug(provider + "User 리프레시 토큰 저장한 후 : {}", socialUser.getRefreshToken());
            userRepository.saveAndFlush(socialUser);

            socialLoginData = SocialLoginData.builder()
                    .userId(socialUser.getId())
                    .nickname(socialUser.getNickname())
                    .statusMessage(socialUser.getStatusMessage())
                    .accessToken(accessToken.getToken())
                    .isFirst(socialLoginResultData.isFirst())
                    .build();

            log.info("social 로그인 성공 후 반환 값 : {}", socialLoginData);
            int cookieMaxAge = (int) refreshTokenExpiry / 60;

            CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);
            CookieUtil.addCookie(httpServletResponse, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        } catch (IllegalArgumentException e) {
            log.error("로그인 실패 : {}", e.getMessage());
            throw new IllegalArgumentException(provider + "로부터 user 정보를 가져오지 못했습니다.");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        return socialLoginData;

    }

    // 카카오에서 access token 받아오기
    public String getSocialAccessToken(String code, String provider) {

        String socialAccessToken = "";
        try {
            String tokenUri = environment.getProperty(CLIENT_PROVIDER + provider + ".token-uri");
            URL url = new URL(tokenUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String clientId = environment.getProperty(CLIENT_REGISTRATION + provider + ".client-id");
            String clientSecret = environment.getProperty(CLIENT_REGISTRATION + provider + ".client-secret");
            String redirectUri = environment.getProperty(CLIENT_REGISTRATION + provider + ".redirect-uri");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(clientId);
            sb.append("&client_secret=").append(clientSecret);
            sb.append("&redirect_uri=").append(redirectUri);
            sb.append("&code=").append(code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info(provider + "에서 access token 받아오기 response code : {}  ", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder socialResponse = new StringBuilder();

            while ((line = br.readLine()) != null) {
                socialResponse.append(line);
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(socialResponse.toString());

            socialAccessToken = (String) jsonObject.get("access_token");

            br.close();
            bw.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return socialAccessToken;
    }

    // 카카오에서 회원정보 받아오기
    @Transactional
    public SocialLoginResultData getSocialUserInfo(String kakaoAccessToken, String provider) {

        User user = null;
        SocialLoginResultData socialLoginResultData = null;

        try {
            String userInfoUri = environment.getProperty(CLIENT_PROVIDER + provider + ".user-info-uri");
            URL url = new URL(userInfoUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);
            int responseCode = conn.getResponseCode();
            log.info(provider + "에서 사용자 정보 받아온 responseCode : {} ", responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder socialResponse = new StringBuilder();

            while ((line = br.readLine()) != null) {
                socialResponse.append(line);
            }

            log.info(provider + "에서 사용자 정보 가져오기 response body : {} ", socialResponse);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(socialResponse.toString());

            OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(provider, (Map<String, Object>) jsonObject);

            log.info("oauth2attribute test : {}", oAuth2Attribute.getAttributes());
            log.info(provider + "에 등록된 이메일 : {}", oAuth2Attribute.getEmail());

            user = userRepository.findByEmailAndType(oAuth2Attribute.getEmail(), provider.toUpperCase()).orElse(null);

            if (user != null) {
                log.info(provider + "로 로그인을 한 적이 있는 user입니다.");

                // 계정 일시 정지인 경우
                if (user.getStatus() == 'P') {
                    BlockedUser blockedUser = blockedUserRepository.findByUserId(user.getId()).orElseThrow(UserNotFoundException::new);
                    LocalDateTime locaDateTimeNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

                    // 계정 정지 기간이 이미 기간이 지난 경우
                    if (blockedUser.getEndDate().isBefore(locaDateTimeNow)) {
                        LocalDateTime nowLocalTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

                        log.info("계정 일시 정지 해제 시간 : {}", nowLocalTime);
                        user.updateUserStatusToActive(nowLocalTime);
                        log.info("계정 일시 정지 풀고 난 후 user status : {}", user.getStatus());

                        socialLoginResultData = SocialLoginResultData.builder()
                                .user(userRepository.saveAndFlush(user))
                                .isFirst(false)
                                .build();

                        return socialLoginResultData;
                    }

                    throw new UnAuthorizedException("pause");
                }
                // 계정 영구 정지인 경우
                else if (user.getStatus() == 'O') {
                    throw new UnAuthorizedException("out");
                }
                // 로그인 한 적 있는 정상 계정인 경우
                else {
                    socialLoginResultData = SocialLoginResultData.builder()
                            .user(user)
                            .isFirst(false)
                            .build();
                }
            } else {
                log.info(provider + " 로그인 최초입니다.");

                String nickname = "하팅" + (userRepository.count() + 1);

                UUID uuid = UUID.randomUUID();
                String shortUuid = parseToShortUUID(uuid.toString());
                log.info("uuid short version : {}", shortUuid);

                if (userRepository.findById(shortUuid).isPresent()) {
                    log.info("uuid 중복입니다.");
                    uuid = UUID.randomUUID();
                    shortUuid = parseToShortUUID(uuid.toString());
                }

                user = User.builder().id(shortUuid).type(provider.toUpperCase()).email(oAuth2Attribute.getEmail()).nickname(nickname).build();

                socialLoginResultData = SocialLoginResultData.builder()
                        .user(userRepository.saveAndFlush(user))
                        .isFirst(true)
                        .build();

                return socialLoginResultData;
            }
        } catch (UnAuthorizedException e) {
            log.error("로그인 한 회원 status : {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        } catch (IOException | ParseException e) {
            log.error(e.getMessage());
        }
        return socialLoginResultData;
    }

    public static String parseToShortUUID(String uuid) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(uuid.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, digest);
            StringBuilder sb = new StringBuilder(number.toString(36));

            while (sb.length() < 10) {
                sb.insert(0, '0');
            }

            return sb.substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

}
