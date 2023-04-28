package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.api.data.HeartListData;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.HeartService;
import com.chillin.hearting.db.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private static final String MESSAGE_SUCCESS = "success";
    private static final String FIND_ALLHEARTS_SUCCESS = "도감용 하트 리스트 조회에 성공했습니다.";
    private static final String FIND_MSGHEARTS_SUCCESS = "메시지용 하트 리스트 조회에 성공했습니다.";

    @GetMapping("")
    public ResponseEntity<ResponseDTO> findAllHearts(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        List<HeartData> allHearts = heartService.findAllHearts(user);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(FIND_ALLHEARTS_SUCCESS).data(HeartListData.builder().hearts(allHearts).build()).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO> findUserHearts(@PathVariable("userId") String userId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        List<HeartData> messageHearts = heartService.findMessageHearts("-1".equals(userId) ? null : user);
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(FIND_MSGHEARTS_SUCCESS).data(HeartListData.builder().hearts(messageHearts).build()).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
