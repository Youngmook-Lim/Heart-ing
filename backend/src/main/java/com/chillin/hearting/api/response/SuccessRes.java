package com.chillin.hearting.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuccessRes {
    
    private String message;

    // 성공 메시지
    public static SuccessRes make(String message) {
        return SuccessRes.builder()
                .message(message)
                .build();
    }
}