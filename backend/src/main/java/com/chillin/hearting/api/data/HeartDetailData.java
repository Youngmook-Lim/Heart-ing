package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Heart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
@Builder
public class HeartDetailData implements Data {
    private Long heartId;
    private String name;
    private String heartUrl;
    private String shortDescription;
    private String longDescription;
    private String type;
    private String acqCondition;
    private Boolean isLocked;
    private Boolean isAcq;
    private List<HeartConditionData> conditions;

    public static HeartDetailData of(Heart heart) {
        return HeartDetailData.builder()
                .heartId(heart.getId())
                .name(heart.getName())
                .heartUrl(heart.getImageUrl())
                .shortDescription(heart.getShortDescription())
                .longDescription(heart.getLongDescription())
                .type(heart.getType())
                .acqCondition(heart.getAcqCondition())
                .isLocked(true)
                .build();
    }
}
