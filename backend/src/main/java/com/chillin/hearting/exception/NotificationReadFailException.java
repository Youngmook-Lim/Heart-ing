package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotificationReadFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "알림 읽기에 실패하였습니다.";

    public NotificationReadFailException() {
        super(DEFAULT_MESSAGE);
    }

    public NotificationReadFailException(String msg) {
        super(msg);
    }
}