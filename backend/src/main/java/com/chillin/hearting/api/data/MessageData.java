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
public class MessageData implements Data {

    private long messageId;
    private String title;
    private long heartId;
    private String heartName;
    private String heartUrl;
    private long emojiId;
    private String emojiName;
    private String emojiUrl;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;

    @Getter(onMethod_ = {@JsonProperty("isRead")})
    @Setter(onMethod_ = {@JsonProperty("isRead")})
    private boolean isRead;
    @Getter(onMethod_ = {@JsonProperty("isReported")})
    @Setter(onMethod_ = {@JsonProperty("isReported")})
    private boolean isReported;
    @Getter(onMethod_ = {@JsonProperty("isStored")})
    @Setter(onMethod_ = {@JsonProperty("isStored")})
    private boolean isStored;

    private String content;
    private String shortDescription;

}
