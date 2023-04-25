package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 메시지를 찾을 수 없습니다.";

    public MessageNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public MessageNotFoundException(String msg) {
        super(msg);
    }
}