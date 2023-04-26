package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MessageAlreadyReportedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "해당 메시지는 이미 신고되었습니다.";

    public MessageAlreadyReportedException() {
        super(DEFAULT_MESSAGE);
    }

    public MessageAlreadyReportedException(String msg) {
        super(msg);
    }
}