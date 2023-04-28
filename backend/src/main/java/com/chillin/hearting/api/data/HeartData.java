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
    private String type;
    @Builder.Default
    @Getter(onMethod_ = {@JsonProperty("isLocked")})
    private Boolean isLocked = true;


    public static HeartData of(Heart heart, boolean isLocked) {
        return HeartData.builder()
                .heartId(heart.getId())
                .name(heart.getName())
                .heartUrl(heart.getImageUrl())
                .type(heart.getType())
                .isLocked(isLocked)
                .build();
    }

    public void unLock() {
        this.isLocked = false;
    }

    public void setLock() {
        this.isLocked = true;
    }
}
