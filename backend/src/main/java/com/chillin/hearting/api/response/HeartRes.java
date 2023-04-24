package com.chillin.hearting.api.response;

import com.chillin.hearting.db.domain.Heart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartRes {
    private Long id;
    private String name;
    private String imageUrl;
    private String type;
    private boolean isLocked = true;

//    @Builder
//    public HeartRes(Heart heart) {
//        this.id = heart.getId();
//        this.name = heart.getName();
//        this.imageUrl = heart.getImageUrl();
//        this.type = heart.getType();
//    }

    @Builder
    public HeartRes(Heart heart, boolean isLocked) {
        this.id = heart.getId();
        this.name = heart.getName();
        this.imageUrl = heart.getImageUrl();
        this.type = heart.getType();
        this.isLocked = isLocked;
    }

    public void unLock() {
        this.isLocked = false;
    }
}

