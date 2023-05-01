package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.request.LoginTestReq;
import com.chillin.hearting.api.request.UpdateNicknameReq;
import com.chillin.hearting.api.request.UpdateStatusMessageReq;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.UserService;
import com.chillin.hearting.api.service.UserTestService;
import com.chillin.hearting.db.domain.User;
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


    @GetMapping("/guests/social/{code}")
    public ResponseEntity<ResponseDTO> kakaoLogin(@PathVariable("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException, IllegalArgumentException {

        Data socialLoginData = userService.kakaoLogin(code, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("소셜 로그인 성공")
                .data(socialLoginData)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/guests/test/login")
    public ResponseEntity<ResponseDTO> testLogin(@RequestBody LoginTestReq loginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Data socialLoginData = userTestService.testLogin(loginReq, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("테스트용 로그인 성공")
                .data(socialLoginData)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/nickname")
    public ResponseEntity<ResponseDTO> updateNickname(@RequestBody UpdateNicknameReq updateNicknameReq, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");

        Data data = userService.updateNickname(user.getId(), updateNicknameReq.getNickname());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("닉네임 변경 성공")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/status-message")
    public ResponseEntity<ResponseDTO> updateStatusMessage(@RequestBody UpdateStatusMessageReq updateStatusMessageReq, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");

        Data data = userService.updateStatusMessage(user.getId(), updateStatusMessageReq.getStatusMessage());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("상태메시지 변경 성공")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/logout")
    public ResponseEntity<ResponseDTO> logoutUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = (User) httpServletRequest.getAttribute("user");

        userService.deleteRefreshToken(user.getId(), httpServletRequest, httpServletResponse);


        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("로그아웃 성공")
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 하트판 주인 정보 조회
    @GetMapping("/guests/{userId}")
    public ResponseEntity<ResponseDTO> getBoardOwnerInformation(@PathVariable("userId") String userId) {

        Data data = userService.getBoardOwnerInformation(userId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("하트판 주인 정보 반환합니다.")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // access token 재발급
    @GetMapping("/users/access-token")
    public ResponseEntity<ResponseDTO> reissueAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Data data = userService.reissueAccessToken(httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("access token 재발급 성공")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
