package com.chillin.hearting.api.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TwitterLoginReq {

    private String oauthToken;

    private String oauthVerifier;
}
