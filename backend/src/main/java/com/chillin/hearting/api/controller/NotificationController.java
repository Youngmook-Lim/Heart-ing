package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.NotificationService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotificationListFailException;
import com.chillin.hearting.exception.NotificationReadFailException;
import com.chillin.hearting.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private static final String SUCCESS = "success";
    private static final String SUCCESS_MESSAGE_GET_NOTIFICATIONS = "유저의 알림 조회에 성공하였습니다.";
    private static final String SUCCESS_MESSAGE_READ_NOTIFICATIONS = "해당 알림이 성공적으로 읽음처리 되었습니다.";
    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getNotifications(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");

        // Check if user has permissions
        if (user == null) {
            throw new UnAuthorizedException();
        }

        Data notificationData = notificationService.getNotifications(user.getId());

        if (notificationData == null) {
            throw new NotificationListFailException();
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message(SUCCESS_MESSAGE_GET_NOTIFICATIONS)
                .data(notificationData)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/{notificationId}")
    public ResponseEntity<ResponseDTO> readNotification(@PathVariable("notificationId") Long notificationId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");

        // Check if user has permissions
        if (user == null) {
            throw new UnAuthorizedException();
        }

        Long result = notificationService.readNotification(notificationId);

        if (result == null) {
            throw new NotificationReadFailException();
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message(SUCCESS_MESSAGE_READ_NOTIFICATIONS)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
