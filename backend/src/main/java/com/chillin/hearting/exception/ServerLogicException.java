package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerLogicException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "서버 로직이 잘못되었습니다. 관리자에게 문의해 주세요.";

    public ServerLogicException() {
        super(DEFAULT_MESSAGE);
    }

    public ServerLogicException(String msg) {
        super(DEFAULT_MESSAGE + '\n' + msg);
    }
}