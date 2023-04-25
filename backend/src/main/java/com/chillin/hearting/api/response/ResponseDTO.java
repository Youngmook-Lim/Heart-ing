package com.chillin.hearting.api.response;

import com.chillin.hearting.api.data.Data;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDTO {

    private String status;
    private String message;
    private Data data;

    // 성공 메시지
//    public static ResponseDTO make(String status, String message, Data data) {
//        return ResponseDTO.builder()
//                .status(status)
//                .message(message)
//                .data(data)
//                .build();
//    }
}