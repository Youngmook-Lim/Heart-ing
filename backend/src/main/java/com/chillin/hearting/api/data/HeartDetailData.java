package com.chillin.hearting.api.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
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
    private String isLocked;
}
