package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.MessageReceivedService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.MessageDetailFailException;
import com.chillin.hearting.exception.ReceivedMessagesListFailException;
import com.chillin.hearting.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages/received")
@RequiredArgsConstructor
public class MessageReceivedController {

    private static final String SUCCESS = "success";
    private final MessageReceivedService messageReceivedService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO> getReceivedMessages(@PathVariable("userId") String userId, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");

        // Check if the user is requesting his own page
        boolean isSelf = user != null && userId.equals(user.getId());

        Data data = messageReceivedService.getReceivedMessages(userId, isSelf);

        if (data == null) {
            throw new ReceivedMessagesListFailException();
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("받은메시지 리스트가 성공적으로 반환되었습니다.")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/detail/{messageId}")
    public ResponseEntity<ResponseDTO> getMessageDetail(@PathVariable("messageId") long messageId, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");

        if (user == null) {
            throw new UnAuthorizedException();
        }

        Data data = messageReceivedService.getMessageDetail(messageId, user.getId());

        if (data == null) {
            throw new MessageDetailFailException();
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("메시지 상세가 성공적으로 반환되었습니다.")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
