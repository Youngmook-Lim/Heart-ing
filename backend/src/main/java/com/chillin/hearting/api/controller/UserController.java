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

/**
 * {@code UserController}는 회원과 관련된 API를 처리하는 컨트롤러입니다.
 *
 * @author wjdwn03
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private static final String SUCCESS = "success";

    private final UserService userService;

    private final OAuthService oAuthService;


    /**
     * (트위터 제외) 소셜 로그인 처리합니다.
     *
     * @param provider            소셜 회사명
     * @param code                소셜에서 넘겨준 인가코드
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 로그인 처리된 유저 정보를 {@code ResponseEntity}로 반환합니다.
     * @throws NotFoundException
     * @throws IllegalArgumentException
     */
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

    /**
     * 트위터 로그인할 redirect url을 생성합니다.
     *
     * @return 생성된 redirect url을 담은 {@code ResponseEntity}로 반환합니다.
     */
    @GetMapping("/guests/twitter/redirect-url")
    public ResponseEntity<ResponseDTO> twitterLogin() {
        Data authenticationUrl = oAuthService.getTwitterRequestToken();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("트위터 로그인 redirect URL 얻어오기 성공!")
                .data(authenticationUrl)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    /**
     * 트위터에서 받아온 코드로 로그인 처리합니다.
     *
     * @param twitterLoginReq     트위터에서 받아온 코드
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 로그인 처리된 유저 정보를 {@code ResponseEntity}로 반환합니다.
     */
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

    /**
     * 관리자 계정 로그인 처리합니다.
     *
     * @param loginReq            관리자 계정의 id를 담은 객체
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 로그인 처리된 관리자 정보를 {@code ResponseEntity}로 반환합니다.
     */
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

    /**
     * 닉네임 수정합니다.
     *
     * @param updateNicknameReq  수정할 닉네임
     * @param httpServletRequest
     * @return 성공 시 수정된 닉네임 정보를 {@code ResponseEntity}로 반환합니다.
     */
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

    /**
     * 상태메시지 수정합니다.
     *
     * @param updateStatusMessageReq 수정할 상태메시지
     * @param httpServletRequest
     * @return 성공 시 수정된 상태메시지 정보를 {@code ResponseEntity}로 반환합니다.
     */
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

    /**
     * 로그아웃 처리합니다.
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 메시지를 반환합니다.
     */
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

    /**
     * 하트판 주인 정보를 조회합니다.
     *
     * @param userId 조회할 하트판 주인의 id
     * @return 성공 시 닉네임, 상태메시지, 누적 수신 메시지 수를 담은 {@code ResponseEntity}로 반환합니다.
     */
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

    /**
     * access token을 재발급합니다.
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return 성공 시 재발급한 access token을 {@code ResponseEntity}로 반환합니다.
     */
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
