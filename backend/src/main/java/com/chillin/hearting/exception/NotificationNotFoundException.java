package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 알림을 찾을 수 없습니다.";

    public NotificationNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NotificationNotFoundException(String msg) {
        super(msg);
    }
}