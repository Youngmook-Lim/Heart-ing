package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.SocialLoginData;
import com.chillin.hearting.api.request.LoginTestReq;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.UserNotFoundException;
import com.chillin.hearting.jwt.AuthToken;
import com.chillin.hearting.jwt.AuthTokenProvider;
import com.chillin.hearting.oauth.domain.AppProperties;
import com.chillin.hearting.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTestService {

    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;

    private final AppProperties appProperties;


    @Transactional
    public SocialLoginData testLogin(LoginTestReq loginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User loginuser = userRepository.findById(loginReq.getId()).orElseThrow(UserNotFoundException::new);

        Date now = new Date();

        AuthToken accessToken = authTokenProvider.createAuthToken(
                loginReq.getId(),
                "ROLE_USER",
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();


        AuthToken refreshToken = authTokenProvider.createAuthToken(
                new Date(now.getTime() + refreshTokenExpiry)
        );

        loginuser.saveRefreshToken(refreshToken.getToken());

        SocialLoginData socialLoginData = SocialLoginData.builder().userId(loginuser.getId()).nickname(loginuser.getNickname()).accessToken(accessToken.getToken()).build();

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, "refreshToken");

        CookieUtil.addCookie(httpServletResponse, "refreshToken", refreshToken.getToken(), cookieMaxAge);

        return socialLoginData;


    }

}
