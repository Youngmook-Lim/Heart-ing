package com.chillin.hearting.exception;

public class JwtExpiredException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "토큰 기간이 만료되었습니다.";

    public JwtExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public JwtExpiredException(String msg) {
        super(msg);
    }

}
