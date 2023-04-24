package com.chillin.hearting.api.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageRes {

    private String status;
    private String message;
    private Map<String, Object> data;
    
}