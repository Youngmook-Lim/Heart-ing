package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.UserService;
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

    @GetMapping(value = "/guests/kakao/{code}")
    public ResponseEntity<ResponseDTO> kakaoLogin(@PathVariable("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException, IllegalArgumentException {
        log.debug("카카오 로그인 시작!");

        String kakaoAccessToken = userService.getKakaoAccessToken(code);

        Data socialLoginData = userService.kakaoLogin(kakaoAccessToken, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("소셜 로그인 성공")
                .data(socialLoginData)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
