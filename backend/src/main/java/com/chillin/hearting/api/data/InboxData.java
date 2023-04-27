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
public class InboxData {
    private Long messageId;
    private String title;
    private String messageContent;
    private Long heartId;
    private String heartUrl;
    private Long emojiId;
    private String emojiName;
    private String emojiUrl;
    private LocalDateTime createdDate;

    public static InboxData of(Message message) {
        Heart heart = message.getHeart();
        Emoji emoji = message.getEmoji();
        return InboxData.builder()
                .messageId(message.getId())
                .title(message.getTitle())
                .messageContent(message.getContent())
                .heartId(heart.getId())
                .heartUrl(heart.getImageUrl())
                .emojiId((emoji != null) ? emoji.getId() : null)
                .emojiName((emoji != null) ? emoji.getName() : null)
                .emojiUrl((emoji != null) ? emoji.getImageUrl() : null)
                .createdDate(message.getCreatedDate())
                .build();

    }
}
