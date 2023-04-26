package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = -2238030302650813813L;

    public static final String DEFAULT_MESSAGE = "계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.";

    public UnAuthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnAuthorizedException(String msg) {
        super(msg);
    }
}