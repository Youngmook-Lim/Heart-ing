package com.chillin.hearting.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorRes {

    private String status;
    private String message;

    public static ErrorRes make(String message) {
        return ErrorRes.builder()
                .status("fail")
                .message(message)
                .build();
    }

}