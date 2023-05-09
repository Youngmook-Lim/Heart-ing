package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.NotificationService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotificationListFailException;
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
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private static final String SUCCESS = "success";
    private static final String SUCCESS_MESSAGE_GET_NOTIFICATIONS = "유저의 알림 조회에 성공하였습니다.";
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO> getNotifications(@PathVariable("userId") String userId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");

        // Check if user has permissions
        if (user == null) {
            throw new UnAuthorizedException();
        }

        Data notificationData = notificationService.getNotifications(userId);

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
}
