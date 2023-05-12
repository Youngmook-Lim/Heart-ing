package com.chillin.hearting.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ReportData implements Data {

    @Getter(onMethod_ = {@JsonProperty("isLoggedInUser")})
    @Setter(onMethod_ = {@JsonProperty("isLoggedInUser")})
    private boolean isLoggedInUser;
}
