package com.chillin.hearting.api.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class SentMessageData {
    private Long messageId;
    private String title;
    private Long heartId;
    private String heartName;
    private Long emojiId;
    private String emojiUrl;
    private String emojiName;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
}
