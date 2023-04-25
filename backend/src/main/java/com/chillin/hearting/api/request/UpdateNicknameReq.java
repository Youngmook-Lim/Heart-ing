package com.chillin.hearting.api.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UpdateNicknameReq {

    private String nickname;
}
