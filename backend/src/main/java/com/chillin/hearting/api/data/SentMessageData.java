package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class SentMessageData implements Data {
    private Long messageId;
    private String title;
    private String shortDescription;
    private String content;
    private Long heartId;
    private String heartName;
    private String heartUrl;
    private Long emojiId;
    private String emojiName;
    private String emojiUrl;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;

    public static SentMessageData of(Message message) {
        Emoji emoji = message.getEmoji();
        Heart heart = message.getHeart();
        return SentMessageData.builder()
                .messageId(message.getId())
                .title(message.getTitle())
                .shortDescription(heart.getShortDescription())
                .heartId(heart.getId())
                .heartName(heart.getName())
                .heartUrl(heart.getImageUrl())
                .emojiId((emoji != null) ? emoji.getId() : null)
                .emojiName((emoji != null) ? emoji.getName() : null)
                .emojiUrl((emoji != null) ? emoji.getImageUrl() : null)
                .createdDate(message.getCreatedDate())
                .expiredDate(message.getExpiredDate())
                .build();
    }
}
