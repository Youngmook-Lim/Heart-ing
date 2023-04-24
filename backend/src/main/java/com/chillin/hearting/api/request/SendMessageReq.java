package com.chillin.hearting.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class SendMessageReq {

    @NotBlank
    private long heartId;
    @NotBlank
    private String senderId;
    @NotBlank
    private String receiverId;
    
    private String content;
}
