package com.chillin.hearting.api.request;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UpdateStatusMessageReq {

    // 상태메시지
    @Size(max = 16)
    private String statusMessage;

}
