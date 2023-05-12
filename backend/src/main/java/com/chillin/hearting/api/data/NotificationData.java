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
    private String heartName;
    private String heartUrl;
    private Long messageId;
    private LocalDateTime createdDate;
    private String type;

    @Getter(onMethod_ = {@JsonProperty("isChecked")})
    @Setter(onMethod_ = {@JsonProperty("isChecked")})
    private boolean isChecked;
}
