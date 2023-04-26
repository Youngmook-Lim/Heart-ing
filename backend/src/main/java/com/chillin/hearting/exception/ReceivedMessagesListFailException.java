package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReceivedMessagesListFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "받은메시지 리스트 반환에 실패하였습니다.";

    public ReceivedMessagesListFailException() {
        super(DEFAULT_MESSAGE);
    }

    public ReceivedMessagesListFailException(String msg) {
        super(msg);
    }
}