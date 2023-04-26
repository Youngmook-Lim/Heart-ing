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
public class InboxDTO {
    private Long messageId;
    private String messageTitle;
    private String messageContent;
    private Long heartId;
    private String heartImage;
    private String emojiName;
    private String emojiUrl;
    private LocalDateTime createdDate;

    public static InboxDTO of(Message message) {
        Heart heart = message.getHeart();
        Emoji emoji = message.getEmoji();
        return InboxDTO.builder()
                .messageId(message.getId())
                .messageTitle(message.getTitle())
                .messageContent(message.getContent())
                .heartId(heart.getId())
                .heartImage(heart.getImageUrl())
                .emojiName((emoji != null) ? emoji.getName() : null)
                .emojiUrl((emoji != null) ? emoji.getImageUrl() : null)
                .createdDate(message.getCreatedDate())
                .build();

    }
}
