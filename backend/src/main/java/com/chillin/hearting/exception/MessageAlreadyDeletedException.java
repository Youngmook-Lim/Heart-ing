package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MessageAlreadyDeletedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 메시지는 이미 삭제되었습니다.";

    public MessageAlreadyDeletedException() {
        super(DEFAULT_MESSAGE);
    }

    public MessageAlreadyDeletedException(String msg) {
        super(msg);
    }
}