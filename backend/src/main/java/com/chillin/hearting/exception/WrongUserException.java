package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongUserException extends RuntimeException {

    public WrongUserException() {
        super("잘못된 유저입니다.");
    }

    public WrongUserException(String msg) {
        super(msg);
    }
}