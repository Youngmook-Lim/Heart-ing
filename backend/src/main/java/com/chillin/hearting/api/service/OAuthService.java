package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.SocialLoginBeforeTokenIssueData;
import com.chillin.hearting.api.data.SocialLoginData;
import com.chillin.hearting.api.data.TwitterRedirectData;
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
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * {@code OAuthService}는 소셜 로그인 관련 로직을 처리하는 서비스입니다.
 *
 * @author wjdwn03
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String CLIENT_PROVIDER = "spring.security.oauth2.client.provider.";
    private static final String CLIENT_REGISTRATION = "spring.security.oauth2.client.registration.";

    private static final String EMPTY_USER_INFO = "로부터 user 정보를 가져오지 못했습니다.";

    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final Environment environment;
    private final UserService userService;
    private final MigrationService migrationService;
    private final MessageService messageService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${twitter.consumer-key}")
    private String CONSUMER_KEY;

    @Value("${twitter.consumer-secret}")
    private String CONSUMER_SECRET;

    @Value("${twitter.redirect-uri}")
    private String TWITTER_REDIRECT_URI;


    @Value("${app.auth.refresh-token-expiry}")
    private long refreshTokenExpiry;


    /**
     * 트위터를 제외한 소셜 로그인을 처리합니다.
     *
     * @param code                provider에서 제공한 인가 코드
     * @param provider            소셜 회사명
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 user id, 닉네임, 상태메시지, access token, isFirst(회원가입인지 아닌지)를 담은 SocialLoginData 타입의 객체를 반환합니다.
     * @throws NotFoundException 소셜에서 회원 정보를 받아오지 못한 경우
     */
    @Transactional
    public SocialLoginData socialLogin(String code, String provider, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException {

        String socialAccessToken = getSocialAccessToken(code, provider);

        SocialLoginData socialLoginData = null;

        try {
            SocialLoginBeforeTokenIssueData socialLoginBeforeTokenIssueData = getSocialUserInfo(socialAccessToken, provider);
            log.info("getSocialUserInfo 리턴값 : {}", socialLoginBeforeTokenIssueData);

            socialLoginData = issueTokenCookie(socialLoginBeforeTokenIssueData, provider, httpServletRequest, httpServletResponse);


        } catch (IllegalArgumentException e) {
            log.error("로그인 실패 : {}", e.getMessage());
            throw new IllegalArgumentException(provider + EMPTY_USER_INFO);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        return socialLoginData;

    }

    /**
     * (트위터 제외) 카카오, 구글에서 제공한 인가코드로 access token을 받아옵니다.
     *
     * @param code     카카오, 구글에서 제공한 인가코드
     * @param provider 소셜 회사명
     * @return 성공 시 소셜에서 받아온 access token을 String 타입으로 반환합니다.
     */
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
            throw new UnAuthorizedException(provider + "에서 access token을 받아올 때 문제가 있었습니다.");
        }

        return socialAccessToken;
    }

    /**
     * (트위터 제외) 소셜에서 받아온 access token으로 사용자 정보를 요청합니다.
     *
     * @param socialAccessToken 소셜에서 넘겨준 access toekn
     * @param provider          소셜 회사명
     * @return 성공 시 소셜 로그인 처리된 user 객체와 회원가입인지 아닌지 여부를 보여주는 isFirst를 담은 SocialLoginBeforeTokenIssueData 타입의 객체를 반환한다.
     */
    @Transactional
    public SocialLoginBeforeTokenIssueData getSocialUserInfo(String socialAccessToken, String provider) {

        SocialLoginBeforeTokenIssueData socialLoginBeforeTokenIssueData = null;

        try {
            String userInfoUri = environment.getProperty(CLIENT_PROVIDER + provider + ".user-info-uri");
            URL url = new URL(userInfoUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + socialAccessToken);
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

            OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(provider, jsonObject);

            log.info("oauth2attribute test : {}", oAuth2Attribute.getAttributes());
            log.info(provider + "에 등록된 이메일 : {}", oAuth2Attribute.getEmail());


            socialLoginBeforeTokenIssueData = checkSocialUserInfoFromDB(oAuth2Attribute.getEmail(), provider);
        } catch (UnAuthorizedException e) {
            log.error("로그인 한 회원 status : {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        } catch (IOException | ParseException e) {
            log.error(e.getMessage());
            throw new UserNotFoundException(provider + "에서 사용자 정보를 받아오지 못했습니다.");
        }
        return socialLoginBeforeTokenIssueData;
    }

    /**
     * 최초 회원가입 시 생성한 uuid를 10자로 줄여준다.
     *
     * @param uuid 10자로 줄여줄 uuid
     * @return 길이를 10자로 줄인 String 타입의 id를 반환한다.
     */
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

    /**
     * 유저가 트위터 로그인할 링크를 생성할 때 필요한 정보를 트위터에 요청한다.
     *
     * @return 클라이언트에서 redirect 할 url을 담은 TwitterRedirectData 타입의 객체를 반환한다.
     */
    public TwitterRedirectData getTwitterRequestToken() {

        log.info("트위터 request token 얻으러 들어옴.");

        // spring에서 twitter api를 사용하기 위해 oauth1 타입의 트위터 연결 생성
        OAuth1Operations oAuth1Operations = new TwitterConnectionFactory(CONSUMER_KEY, CONSUMER_SECRET).getOAuthOperations();

        // 이메일 정보를 받아오기 위해 파라미터 설정
        OAuth1Parameters params = new OAuth1Parameters();
        params.set("include_email", "true");

        // 사용자 인증 후 리다이렉트할 주소를 담아서 request token 요청하여 받아옴.
        OAuthToken requestToken = oAuth1Operations.fetchRequestToken(TWITTER_REDIRECT_URI, params);

        // request token을 담아서 사용자 인증 url 생성(트위터 로그인 화면창)
        String authenticationUrl = oAuth1Operations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        return TwitterRedirectData.builder().redirectUrl(authenticationUrl).build();
    }

    /**
     * 트위터에서 넘겨준 코드로 유저 정보를 요청하여 로그인 처리를 해준다.
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param oauthToken          트위터에게 access token을 요청할 때 필요한 request 토큰
     * @param oauthVerifier       트위터에게 access token을 요청할 때 필요한 oauthVerifier
     * @param provider            트위터
     * @return 성공 시 user id, 닉네임, 상태메시지, access token, isFirst(회원가입인지 아닌지)를 담은 SocialLoginData 타입의 객체를 반환합니다.
     */
    @Transactional
    public SocialLoginData getTwitterUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String oauthToken, String oauthVerifier, String provider) {

        SocialLoginData socialLoginData = null;

        try {
            // spring에서 twitter api를 사용하기 위해 oauth1 타입의 트위터 연결 생성
            OAuth1Operations oauthOperations = new TwitterConnectionFactory(CONSUMER_KEY, CONSUMER_SECRET).getOAuthOperations();

            // access token 받아올 때에도 request token이 필요하기 때문에 OAuthToken 객체 생성
            OAuthToken requestToken = new OAuthToken(oauthToken, null);

            // access token 받아옴
            OAuthToken accessToken = oauthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);

            // 이제 트위터에서 제공하는 api에 접근할 때 마다 항상 같이 가져가야 하는 친구들을 모두 담아서 TwitterTemplate 객체 생성
            Twitter twitter = new TwitterTemplate(CONSUMER_KEY, CONSUMER_SECRET, accessToken.getValue(), accessToken.getSecret());

            // 현재 인증된 사용자의 트위터 프로필 가져옴. -> 여기엔 이메일 없음.
            TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();

            log.info("트위터 로그인 한 유저 정보 : {}", twitterProfile.getId());

            // Get email address
            RestTemplate restTemplate = ((TwitterTemplate) twitter).getRestTemplate();
            ResponseEntity<JsonNode> response = restTemplate.getForEntity("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true", JsonNode.class);
            JsonNode jsonResponse = response.getBody();
            String email = jsonResponse.get("email").asText();

            log.info("트위터 로그인 한 유저의 이메일 정보 : {}", email);

            SocialLoginBeforeTokenIssueData socialLoginBeforeTokenIssueData = checkSocialUserInfoFromDB(email, provider);

            socialLoginData = issueTokenCookie(socialLoginBeforeTokenIssueData, provider, httpServletRequest, httpServletResponse);

        } catch (NullPointerException e) {
            log.info(e.getMessage());
            throw new UnAuthorizedException(provider + EMPTY_USER_INFO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return socialLoginData;

    }

    /**
     * 소셜 측에서 받아온 email로 DB에 insert된 유저인지 확인하고 DB insert하거나 status를 처리합니다.
     *
     * @param email    소셜에서 받아온 유저의 이메일
     * @param provider 소셜 회사명
     * @return DB에 저장된 User와 isFirst(회원가입인지 아닌지)를 담은 SocialLoginBeforeTokenIssueData 타입의 객체를 반환합니다.
     */
    @Transactional
    public SocialLoginBeforeTokenIssueData checkSocialUserInfoFromDB(String email, String provider) {

        User user = userRepository.findByEmailAndType(email, provider.toUpperCase()).orElse(null);

        SocialLoginBeforeTokenIssueData socialLoginBeforeTokenIssueData = null;

        try {

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

                        socialLoginBeforeTokenIssueData = SocialLoginBeforeTokenIssueData.builder()
                                .user(userRepository.saveAndFlush(user))
                                .isFirst(false)
                                .build();

                        return socialLoginBeforeTokenIssueData;
                    }

                    throw new UnAuthorizedException("pause");
                }
                // 계정 영구 정지인 경우
                else if (user.getStatus() == 'O') {
                    throw new UnAuthorizedException("out");
                }
                // 로그인 한 적 있는 정상 계정인 경우
                else {
                    socialLoginBeforeTokenIssueData = SocialLoginBeforeTokenIssueData.builder()
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

                user = User.builder().id(shortUuid).type(provider.toUpperCase()).email(email).nickname(nickname).build();

                socialLoginBeforeTokenIssueData = SocialLoginBeforeTokenIssueData.builder()
                        .user(userRepository.saveAndFlush(user))
                        .isFirst(true)
                        .build();

                return socialLoginBeforeTokenIssueData;
            }
        } catch (UnAuthorizedException e) {
            log.error("로그인 한 회원 status : {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        }
        return socialLoginBeforeTokenIssueData;
    }

    /**
     * JWT 발급, refresh token을 쿠키에 담아줍니다.
     *
     * @param socialLoginBeforeTokenIssueData 토큰을 발급할 유저 정보가 담긴 객체
     * @param provider                        소셜 회사명
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 user id, 닉네임, 상태메시지, access token, isFirst(회원가입인지 아닌지)를 담은 SocialLoginData 타입의 객체를 반환합니다.
     */
    @Transactional
    public SocialLoginData issueTokenCookie(SocialLoginBeforeTokenIssueData socialLoginBeforeTokenIssueData, String provider, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        SocialLoginData socialLoginData = null;

        try {

            User socialUser = socialLoginBeforeTokenIssueData.getUser();

            if (socialUser == null) {
                throw new NotFoundException(provider + EMPTY_USER_INFO);
            }

            if (socialLoginBeforeTokenIssueData.isFirst()) {

                // 레디스에 유저 보낸 하트 정보 등록
                migrationService.migrateUserSentHeart(socialUser.getId());
                migrationService.migrateUserReceivedHeart(socialUser.getId());

                messageService.sendMessage(7L, null, socialUser.getId(), "환영합니다!\uD83D\uDC95", "안녕하세요!( >ᴗ< )\n" +
                        "하팅 개발진의 감사한 마음을 \n" +
                        "모두 모아 첫 번째 하트를 \n" +
                        "보냅니다.❤\uFE0F\uD83D\uDC9B\uD83D\uDC9A\uD83D\uDC99\uD83D\uDC9C\n" +
                        "하트에 전달하고 싶은 마음, \n" +
                        "감정을 담아 주고받아 보세요.\n" +
                        "하팅!", "ADMIN");

                messageService.sendScheduledMessages(socialUser);
            }

            AuthToken accessToken = userService.makeAccessToken(socialUser.getId());

            AuthToken refreshToken = userService.makeRefreshToken();

            log.debug("accessToken : {}", accessToken.getToken());
            log.debug("refreshToken : {}", refreshToken.getToken());

            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            String key = "userToken:" + socialUser.getId();
            valueOperations.set(key, refreshToken.getToken(), 14L, TimeUnit.DAYS);
            log.info("refresh token redis에 저장했다?");


            socialLoginData = SocialLoginData.builder()
                    .userId(socialUser.getId())
                    .nickname(socialUser.getNickname())
                    .statusMessage(socialUser.getStatusMessage())
                    .accessToken(accessToken.getToken())
                    .isFirst(socialLoginBeforeTokenIssueData.isFirst())
                    .build();

            log.info("social 로그인 성공 후 반환 값 : {}", socialLoginData);
            int cookieMaxAge = (int) refreshTokenExpiry / 60;

            CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);
            CookieUtil.addCookie(httpServletResponse, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
        } catch (IllegalArgumentException e) {
            log.error("로그인 실패 : {}", e.getMessage());
            throw new IllegalArgumentException(provider + EMPTY_USER_INFO);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        return socialLoginData;
    }

}
