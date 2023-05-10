package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.api.data.HeartListData;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.HeartService;
import com.chillin.hearting.api.service.UserHeartService;
import com.chillin.hearting.db.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final UserHeartService userHeartService;
    private static final String MESSAGE_SUCCESS = "success";
    private static final String FIND_ALLHEARTS_SUCCESS = "도감용 하트 리스트 조회에 성공했습니다.";
    private static final String FIND_MSGHEARTS_SUCCESS = "메시지용 하트 리스트 조회에 성공했습니다.";
    private static final String FIND_HEART_DETAIL_SUCCESS = "도감 하트 상세 조회에 성공했습니다.";
    private static final String SAVE_USER_HEART_SUCCESS = "하트 획득에 성공했습니다.";


    @GetMapping("")
    public ResponseEntity<ResponseDTO> findAllHearts(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        Data data = heartService.findAllHearts(user);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(FIND_ALLHEARTS_SUCCESS).data(data).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/user-hearts")
    public ResponseEntity<ResponseDTO> findUserHearts(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        List<HeartData> messageHearts = heartService.findUserMessageHearts(user);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(FIND_MSGHEARTS_SUCCESS).data(HeartListData.builder().heartList(messageHearts).build()).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{heartId}")
    public ResponseEntity<ResponseDTO> findHeartDetail(@PathVariable("heartId") Long heartId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        Data data = heartService.findHeartDetail(user, heartId);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(FIND_HEART_DETAIL_SUCCESS).data(data).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/user-hearts/{heartId}")
    public ResponseEntity<ResponseDTO> saveUserHearts(@PathVariable("heartId") Long heartId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        userHeartService.saveUserHearts(user.getId(), heartId);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(SAVE_USER_HEART_SUCCESS).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
