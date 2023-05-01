package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.MessageSentService;
import com.chillin.hearting.db.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages/sent")
@RequiredArgsConstructor
public class MessageSentController {

    private static final String SUCCESS = "success";
    private final MessageSentService messageSentService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getSentMessages(HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");

        Data data = messageSentService.getSentMessages(user.getId());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("보낸 메시지 리스트가 성공적으로 반환되었습니다.")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
