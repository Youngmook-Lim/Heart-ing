package com.chillin.hearting.api.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuccessRes {

    private String status;
    private String message;
    private Map<String, Object> data;

    // 성공 메시지
    public static SuccessRes make(String status, String message, Map<String, Object> data) {
        return SuccessRes.builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }
}