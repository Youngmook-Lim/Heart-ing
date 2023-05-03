package com.chillin.hearting.api.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class HeartListData implements Data {
    private List<HeartData> heartList;

    public HeartListData(List<HeartData> hearts) {
        this.heartList = hearts;
    }
}
