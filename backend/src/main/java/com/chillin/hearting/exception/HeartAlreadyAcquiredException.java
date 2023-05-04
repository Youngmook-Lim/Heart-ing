package com.chillin.hearting.exception;

public class HeartAlreadyAcquiredException extends RuntimeException {

    public HeartAlreadyAcquiredException() {
        super("이미 획득한 메시지 입니다.");
    }

    public HeartAlreadyAcquiredException(String msg) {
        super(msg);
    }
}
