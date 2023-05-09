package com.chillin.hearting.api.request;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UpdateNicknameReq {

    @Size(min = 1, max = 12)
    private String nickname;
}
