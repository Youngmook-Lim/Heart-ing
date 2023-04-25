package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.request.SendMessageReq;
import com.chillin.hearting.api.response.SendMessageRes;
import com.chillin.hearting.api.service.MessageService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.WrongUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<SendMessageRes> sendMessage(@Valid @RequestBody SendMessageReq sendMessageReq, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");

        // Check if logged in user is same as sender
        if (!user.getId().equals(sendMessageReq.getSenderId())) {
            throw new WrongUserException();
        }

        SendMessageRes sendMessageRes = messageService.sendMessage(sendMessageReq.getHeartId(), sendMessageReq.getSenderId(), sendMessageReq.getReceiverId(), sendMessageReq.getTitle(), sendMessageReq.getContent(), null);
        

        return null;
    }

}
