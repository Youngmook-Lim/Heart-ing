package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.*;
import com.chillin.hearting.api.request.LoginTestReq;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.UnAuthorizedException;
import com.chillin.hearting.exception.UserNotFoundException;
import com.chillin.hearting.jwt.AuthToken;
import com.chillin.hearting.jwt.AuthTokenProvider;
import com.chillin.hearting.oauth.domain.AppProperties;
import com.chillin.hearting.oauth.domain.PrincipalDetails;
import com.chillin.hearting.util.CookieUtil;
import com.chillin.hearting.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * {@code UserService}는 로그인을 제외한 모든 유저 관련 로직을 처리하는 서비스입니다.
 *
 * @author wjdwn03
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String ROLE = "ROLE_USER";

    private static final String USER_TOKEN = "userToken:";

    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;


    @Value("${app.auth.refresh-token-expiry}")
    private long refreshTokenExpiry;

    /**
     * 닉네임 수정을 요청한 정보를 DB에 저장합니다.
     *
     * @param userId   수정 요청한 유저 id
     * @param nickname 수정할 닉네임
     * @return 성공 시 사용자가 수정한 닉네임 정보를 담은 UpdateNicknameData 타입의 객체를 반환합니다.
     */
    @Transactional
    public UpdateNicknameData updateNickname(String userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LocalDateTime nowLocalTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        user.updateNickname(nickname, nowLocalTime);

        return UpdateNicknameData.builder()
                .nickname(user.getNickname())
                .build();

    }

    /**
     * 상태메시지 수정을 요청한 정보를 DB에 저장합니다.
     *
     * @param userId        수정 요청한 유저 id
     * @param statusMessage 수정할 상태메시지
     * @return 성공 시 사용자가 수정한 닉네임 정보를 담은 UpdateStatusMessageData 타입의 객체를 반환합니다.
     */
    @Transactional
    public UpdateStatusMessageData updateStatusMessage(String userId, String statusMessage) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LocalDateTime nowLocalTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        user.updateStatusMessage(statusMessage, nowLocalTime);

        return UpdateStatusMessageData.builder()
                .statusMessage(user.getStatusMessage())
                .build();

    }

    /**
     * 로그아웃 시 호출되는 메서드로 DB에 저장된 refresh token을 삭제합니다.
     *
     * @param userId              refresh token을 삭제할 user id
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @Transactional
    public void deleteRefreshToken(String userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.deleteRefreshToken();

        userRepository.save(user);

        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);

    }

    /**
     * 하트판 주인의 정보를 조회합니다.
     *
     * @param userId 조회할 user id
     * @return 성공 시 하트판 주인의 닉네임, 상태메시지, 누적 수신 메시지 수를 담은 HeartBoardOwnerData 타입의 객체를 반환합니다.
     */
    public HeartBoardOwnerData getBoardOwnerInformation(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return HeartBoardOwnerData.builder()
                .nickname(user.getNickname())
                .statusMessage(user.getStatusMessage())
                .messageTotal(user.getMessageTotal())
                .build();
    }

    /**
     * access token을 재발급합니다.
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 재발급한 access token을 담은 ReissuedAccessTokenData 타입의 객체를 반환합니다.
     */
    @Transactional
    public ReissuedAccessTokenData reissueAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String headerAccessToken = HeaderUtil.getAccessToken(httpServletRequest);

        AuthToken authHeaderAccessToken = tokenProvider.convertAuthToken(headerAccessToken);
        Authentication authentication = tokenProvider.getExpiredUser(authHeaderAccessToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        User user = principalDetails.getUser();
        String cookieRefreshToken = CookieUtil.getCookie(httpServletRequest, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        String key = USER_TOKEN + user.getId();
        String redisRefreshToken = "";

        try {
            if (cookieRefreshToken == null) {
                deleteUserToken(user.getId());
                throw new UnAuthorizedException("쿠키에 refresh token이 없습니다. 다시 로그인 해주세요.");
            }

            redisRefreshToken = valueOperations.get(key).toString();

            if (!cookieRefreshToken.equals(redisRefreshToken)) {
                deleteUserToken(user.getId());
                deleteCookieRefreshToken(httpServletRequest, httpServletResponse);
                throw new UnAuthorizedException("redis에 저장되어 있는 refreshToken과 다릅니다. 다시 로그인 해주세요.");
            }

            log.info("redis에 저장한 리프레시 토큰 : {}", redisRefreshToken);

        } catch (NullPointerException e) {
            deleteUserToken(user.getId());
            deleteCookieRefreshToken(httpServletRequest, httpServletResponse);
            throw new UnAuthorizedException("refresh token이 만료되었습니다. 다시 로그인 해주세요.");
        }

        log.debug("쿠키에 담긴 refreshToken : {}", cookieRefreshToken);


        AuthToken accessToken = makeAccessToken(user.getId());

        log.info("정상적으로 액세스토큰 재발급!!!");


        return ReissuedAccessTokenData.builder().accessToken(accessToken.getToken()).build();
    }

    /**
     * access token을 발급합니다.
     *
     * @param userId access token을 생성할 user id
     * @return 성공 시 발급된 access token을 AuthToken 타입의 객체로 반환합니다.
     */
    public AuthToken makeAccessToken(String userId) {

        Date now = new Date();

        return tokenProvider.createAuthToken(
                userId,
                ROLE,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );
    }

    /**
     * refresh token을 발급합니다.
     *
     * @return 성공 시 생성한 refresh token을 AuthToken 타입의 객체로 반환합니다.
     */
    public AuthToken makeRefreshToken() {

        Date now = new Date();

        return tokenProvider.createAuthToken(new Date(now.getTime() + refreshTokenExpiry));
    }

    /**
     * 관리자 계정 로그인을 처리합니다.
     *
     * @param loginReq            관리자 계정 로그인을 요청한 user id가 담긴 객체
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 닉네임, access token, isFirst를 담은 SocialLoginData 타입의 객체를 반환합니다.
     */
    @Transactional
    public SocialLoginData adminLogin(LoginTestReq loginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User loginuser = userRepository.findById(loginReq.getId()).orElseThrow(UserNotFoundException::new);


        Date now = new Date();

        AuthToken accessToken = authTokenProvider.createAuthToken(
                loginReq.getId(),
                "ROLE_ADMIN",
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );


        AuthToken refreshToken = makeRefreshToken();

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String key = USER_TOKEN + loginuser.getId();
        valueOperations.set(key, refreshToken.getToken(), 14L, TimeUnit.DAYS);
        log.info("refresh token redis에 저장했다?");

        SocialLoginData socialLoginData = SocialLoginData.builder().userId(loginuser.getId()).nickname(loginuser.getNickname()).accessToken(accessToken.getToken()).isFirst(false).build();

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);

        CookieUtil.addCookie(httpServletResponse, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        log.info("관리자용 로그인 성공! {}", socialLoginData);

        return socialLoginData;

    }

    /**
     * redis에 저장된 user의 refresh token을 제거합니다.
     *
     * @param userId refresh token을 제거할 user id
     */
    public void deleteUserToken(String userId) {
        redisTemplate.opsForValue().getOperations().delete(USER_TOKEN + userId);
    }

    /**
     * 쿠키에 담긴 refresh token을 삭제합니다.
     *
     * @param httpServletRequest
     * @param httpServletResponse
     */
    public void deleteCookieRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN);
    }


}
