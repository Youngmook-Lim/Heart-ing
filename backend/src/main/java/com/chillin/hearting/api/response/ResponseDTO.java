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
    
}