package com.chillin.hearting.oauth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private final String companyName;
}
