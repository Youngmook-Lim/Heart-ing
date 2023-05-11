package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.request.LoginTestReq;
import com.chillin.hearting.api.request.TwitterLoginReq;
import com.chillin.hearting.api.request.UpdateNicknameReq;
import com.chillin.hearting.api.request.UpdateStatusMessageReq;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.OAuthService;
import com.chillin.hearting.api.service.UserService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private static final String SUCCESS = "success";

    private final UserService userService;

    private final OAuthService oAuthService;


    @GetMapping("/guests/social/{provider}")
    public ResponseEntity<ResponseDTO> socialLogin(@PathVariable("provider") String provider, @RequestParam("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundException, IllegalArgumentException {

        Data socialLoginData = oAuthService.socialLogin(code, provider, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("소셜 로그인 성공")
                .data(socialLoginData)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/guests/twitter/redirect-url")
    public ResponseEntity<ResponseDTO> twitterLogin(HttpServletResponse httpServletResponse) throws IOException {
        Data authenticationUrl = oAuthService.getTwitterRequestToken();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("트위터 로그인 redirect URL 얻어오기 성공!")
                .data(authenticationUrl)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/guests/twitter/user-info")
    public ResponseEntity<ResponseDTO> getTwitterUserInfo(@RequestBody TwitterLoginReq twitterLoginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Data socialLoginData = oAuthService.getTwitterUserInfo(httpServletRequest, httpServletResponse, twitterLoginReq.getOauthToken(), twitterLoginReq.getOauthVerifier(), "twitter");

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("트위터 로그인 성공!!")
                .data(socialLoginData)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/guests/admin/login")
    public ResponseEntity<ResponseDTO> adminLogin(@RequestBody LoginTestReq loginReq, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Data socialLoginData = userService.adminLogin(loginReq, httpServletRequest, httpServletResponse);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("admin 로그인 성공")
                .data(socialLoginData)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/nickname")
    public ResponseEntity<ResponseDTO> updateNickname(@Valid @RequestBody UpdateNicknameReq updateNicknameReq, HttpServletRequest httpServletRequest) {

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
    public ResponseEntity<ResponseDTO> updateStatusMessage(@Valid @RequestBody UpdateStatusMessageReq updateStatusMessageReq, HttpServletRequest httpServletRequest) {
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
