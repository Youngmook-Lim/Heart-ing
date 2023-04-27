package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class InboxDetailData implements Data {
    private Long messageId;
    private Long heartId;
    private String heartName;
    private String heartUrl;
    private String content;
    private LocalDateTime createdDate;
    private String emojiUrl;
    private String emojiName;

    @Builder
    public InboxDetailData(Message message) {
        Heart heart = message.getHeart();

        this.messageId = message.getId();
        this.heartId = heart.getId();
        this.heartName = heart.getName();
        this.heartUrl = heart.getImageUrl();
        this.content = message.getContent();
        this.createdDate = message.getCreatedDate();

        Emoji emoji = message.getEmoji();
        if (emoji != null) {
            this.emojiUrl = emoji.getImageUrl();
            this.emojiName = emoji.getName();
        }
    }
}
