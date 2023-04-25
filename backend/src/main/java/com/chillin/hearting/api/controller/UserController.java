package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.request.LoginTestReq;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.UserService;
import com.chillin.hearting.api.service.UserTestService;
import com.chillin.hearting.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 테스트용 service
    private final UserTestService userTestService;


    @GetMapping(value = "/guests/social/{code}")
    public ResponseEntity<ResponseDTO> kakaoLogin(@PathVariable("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException, IllegalArgumentException {

        Data socialLoginData = userService.kakaoLogin(code, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("소셜 로그인 성공")
                .data(socialLoginData)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/guests/test/login")
    public ResponseEntity<ResponseDTO> testLogin(@RequestBody LoginTestReq loginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Data socialLoginData = userTestService.testLogin(loginReq, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("테스트용 로그인 성공")
                .data(socialLoginData)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
