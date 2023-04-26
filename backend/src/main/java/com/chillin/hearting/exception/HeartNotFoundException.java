package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeartNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 하트를 찾을 수 없습니다.";

    public HeartNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public HeartNotFoundException(String msg) {
        super(msg);
    }
}