package com.chillin.hearting.api.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UpdateStatusMessageReq {

    // 상태메시지
    private String statusMessage;

}
