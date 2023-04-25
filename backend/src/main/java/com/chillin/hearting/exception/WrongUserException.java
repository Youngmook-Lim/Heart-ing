package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongUserException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "잘못된 유저입니다.";

    public WrongUserException() {
        super(DEFAULT_MESSAGE);
    }

    public WrongUserException(String msg) {
        super(msg);
    }
}