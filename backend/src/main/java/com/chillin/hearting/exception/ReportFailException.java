package com.chillin.hearting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReportFailException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "신고 접수가 실패하였습니다.";

    public ReportFailException() {
        super(DEFAULT_MESSAGE);
    }

    public ReportFailException(String msg) {
        super(msg);
    }
}