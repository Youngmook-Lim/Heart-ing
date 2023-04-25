package com.chillin.hearting.exception;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
        super("해당 메시지를 찾을 수 없습니다.");
    }

    public MessageNotFoundException(String msg) {
        super(msg);
    }
}
