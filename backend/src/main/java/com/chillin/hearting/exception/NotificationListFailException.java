package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotificationListFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "알림 리스트 반환에 실패하였습니다.";

    public NotificationListFailException() {
        super(DEFAULT_MESSAGE);
    }

    public NotificationListFailException(String msg) {
        super(msg);
    }
}