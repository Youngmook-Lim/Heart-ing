package com.chillin.hearting.exception;

public class JwtNotExpiredException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "토큰 유효기간이 남아있습니다.";

    public JwtNotExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public JwtNotExpiredException(String msg) {
        super(msg);
    }

}
