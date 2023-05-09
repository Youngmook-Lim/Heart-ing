package com.chillin.hearting.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationData implements Data {

    private Long notificationId;
    private String userId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String type;

    @Getter(onMethod_ = {@JsonProperty("isChecked")})
    @Setter(onMethod_ = {@JsonProperty("isChecked")})
    private boolean isChecked;

    @Getter(onMethod_ = {@JsonProperty("isActive")})
    @Setter(onMethod_ = {@JsonProperty("isActive")})
    private boolean isActive;
}
