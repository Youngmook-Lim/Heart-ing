package com.chillin.hearting.exception;

public class JwtExpiredException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "토큰이 존재하지 않습니ㅏㄷ.";

    public JwtExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public JwtExpiredException(String msg) {
        super(msg);
    }

}
