package com.chillin.hearting.api.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginData implements Data {

    // user 고유 코드
    private String userId;

    // 닉네임
    private String nickname;

    // 상태 메시지
    private String statusMessage;

    // accessToken
    private String accessToken;

    // 회원가입 여부
    @Getter(onMethod_ = {@JsonProperty("isFirst")})
    private boolean isFirst;

}
