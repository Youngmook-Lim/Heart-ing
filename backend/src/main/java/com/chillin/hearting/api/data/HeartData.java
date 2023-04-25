package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Heart;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HeartData extends Data {

    private Long id;
    private String name;
    private String imageUrl;
    private String type;
    private Boolean isLocked = true;

    @Builder
    public HeartData(Heart heart, boolean isLocked) {
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
