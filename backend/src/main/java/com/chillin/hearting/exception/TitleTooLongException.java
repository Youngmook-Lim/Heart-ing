package com.chillin.hearting.exception;

public class TitleTooLongException extends RuntimeException {

    public TitleTooLongException() {
        super("제목이 12자를 초과하였습니다.");
    }

    public TitleTooLongException(String msg) {
        super(msg);
    }
}
