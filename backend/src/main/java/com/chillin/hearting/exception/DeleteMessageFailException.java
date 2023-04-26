package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DeleteMessageFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "메시지 삭제에 실패하였습니다.";

    public DeleteMessageFailException() {
        super(DEFAULT_MESSAGE);
    }

    public DeleteMessageFailException(String msg) {
        super(msg);
    }
}