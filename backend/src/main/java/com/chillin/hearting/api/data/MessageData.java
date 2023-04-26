package com.chillin.hearting.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageData extends Data {

    private long messageId;
    private long heartId;
    private String heartName;
    private String heartUrl;

    @Getter(onMethod_ = {@JsonProperty("isRead")})
    private boolean isRead;


}
