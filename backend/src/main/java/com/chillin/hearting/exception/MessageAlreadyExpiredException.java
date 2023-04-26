package com.chillin.hearting.exception;

public class MessageAlreadyExpiredException extends RuntimeException {

    public MessageAlreadyExpiredException() {
        super("이미 만료된 메시지입니다..");
    }

    public MessageAlreadyExpiredException(String msg) {
        super(msg);
    }
}
