package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmojiFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "이모지 변경이 실패하였습니다.";

    public EmojiFailException() {
        super(DEFAULT_MESSAGE);
    }

    public EmojiFailException(String msg) {
        super(msg);
    }
}