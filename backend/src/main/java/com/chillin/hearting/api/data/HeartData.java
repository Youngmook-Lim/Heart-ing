package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Heart;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HeartData implements Data {

    private Long id;
    private String name;
    private String imageUrl;
    private String type;
    private Boolean isLocked = true;


    public static HeartData of(Heart heart, boolean isLocked) {
        return HeartData.builder()
                .id(heart.getId())
                .name(heart.getName())
                .imageUrl(heart.getImageUrl())
                .type(heart.getType())
                .isLocked(isLocked)
                .build();
    }

    public void unLock() {
        this.isLocked = false;
    }
}
