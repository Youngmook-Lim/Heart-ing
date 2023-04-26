package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MessageDetailFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "메시지 상세 반환에 실패하였습니다.";

    public MessageDetailFailException() {
        super(DEFAULT_MESSAGE);
    }

    public MessageDetailFailException(String msg) {
        super(msg);
    }
}