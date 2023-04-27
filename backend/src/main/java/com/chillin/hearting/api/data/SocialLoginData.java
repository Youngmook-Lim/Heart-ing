package com.chillin.hearting.api.data;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SocialLoginData implements Data {

    // user 고유 코드
    private String userId;

    // 닉네임
    private String nickname;

    // accessToken
    private String accessToken;

    @Builder
    public SocialLoginData(String userId, String nickname, String accessToken) {

        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

}
