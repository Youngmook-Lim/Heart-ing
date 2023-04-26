package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmojiNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 이모지를 찾을 수 없습니다.";

    public EmojiNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EmojiNotFoundException(String msg) {
        super(msg);
    }
}