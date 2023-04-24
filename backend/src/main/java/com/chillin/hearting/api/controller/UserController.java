package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.response.SocialLoginRes;
import com.chillin.hearting.api.response.SuccessRes;
import com.chillin.hearting.api.service.UserService;
import com.chillin.hearting.exception.DuplicateException;
import com.chillin.hearting.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j // log 사용하기 위한 어노테이션
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private static final String SUCCESS = "success";
    private static final String REFRESH_TOKEN = "refreshToken";

    private final UserService userService;

    @GetMapping(value = "/guests/nickname/{nickname}")
    public ResponseEntity<SuccessRes> duplicateNickname(@PathVariable("nickname") String nickname) throws DuplicateException {

        log.debug("중복체크 요청 닉네임 = {}", nickname);

        userService.duplicateNickname(nickname);

        SuccessRes successRes = SuccessRes.builder().message(SUCCESS).build();

        return new ResponseEntity<>(successRes, HttpStatus.OK);
    }

    @GetMapping(value = "/guests/kakao/{code}")
    public ResponseEntity<SocialLoginRes> kakaoLogin(@PathVariable("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException, IllegalArgumentException {
        log.debug("카카오 로그인 시작!");

        String kakaoAccessToken = userService.getKakaoAccessToken(code);

        return userService.kakaoLogin(kakaoAccessToken, httpServletRequest, httpServletResponse);
    }

}
