package com.chillin.hearting.api.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SocialLoginRes {

    // user 고유 코드
    private String userId;

    // 닉네임
    private String nickname;

    // accessToken
    private String accessToken;

    // 상태메시지
    private String statusMessage;

    // 회원별 누적 메시지 수
    private Long messageTotal;

    @Builder
    public SocialLoginRes(String userId, String nickname, String accessToken, String statusMessage, Long messageTotal) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.statusMessage = statusMessage;
        this.messageTotal = messageTotal;
    }

}
