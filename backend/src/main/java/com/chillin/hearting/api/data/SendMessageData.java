package com.chillin.hearting.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageData implements Data {

    private long messageId;
    private long heartId;
    private String heartName;
    private String heartUrl;

    @Getter(onMethod_ = {@JsonProperty("isRead")})
    @Setter(onMethod_ = {@JsonProperty("isRead")})
    private boolean isRead;

    @Getter(onMethod_ = {@JsonProperty("isCheckSender")})
    @Setter(onMethod_ = {@JsonProperty("isCheckSender")})
    private boolean isCheckSender;
}
