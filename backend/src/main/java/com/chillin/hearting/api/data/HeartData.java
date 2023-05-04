package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Heart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HeartData implements Data {

    private Long heartId;
    private String name;
    private String heartUrl;
    private String shortDescription;
    private String type;
    @Builder.Default
    @Getter(onMethod_ = {@JsonProperty("isLocked")})
    private Boolean isLocked = true;
    @Getter(onMethod_ = {@JsonProperty("isAcq")})
    private Boolean isAcq;


    public static HeartData of(Heart heart, boolean isLocked) {
        return HeartData.builder()
                .heartId(heart.getId())
                .name(heart.getName())
                .heartUrl((isLocked) ? getLockedUrl() : heart.getImageUrl())
                .shortDescription(heart.getShortDescription())
                .type(heart.getType())
                .isLocked(isLocked)
                .build();
    }

    public void unLock() {
        this.isLocked = false;
    }

    public void setLock() {
//        this.heartUrl = getLockedUrl();
        this.isLocked = true;
    }

    private static String getLockedUrl() {
        return "https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_lock_1.svg";
    }

    public void setAcq(boolean isAcq) {
        this.isAcq = isAcq;
    }
}
