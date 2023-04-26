package com.chillin.hearting.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class SendMessageReq {

    @NotNull
    private long heartId;

    private String senderId;

    @NotBlank
    private String receiverId;

    @NotBlank
    private String title;

    private String content;

    @Builder
    public SendMessageReq(long heartId, String senderId, String receiverId, String title) {
        this.heartId = heartId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
    }
}
