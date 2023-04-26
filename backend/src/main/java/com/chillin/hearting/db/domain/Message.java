package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Message implements Serializable {

    private static final int EXPIRY_TIME = 24;

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heart_id", nullable = false)
    private Heart heart;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emoji_id") // null 가능
    private Emoji emoji;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id") // null 가능
    private User sender;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(length = 50, name = "title", nullable = false)
    private String title;

    @Column(length = 500, name = "content")
    private String content;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "is_stored", nullable = false)
    private boolean isStored;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_reported", nullable = false)
    private boolean isReported;

    @Column(length = 20, name = "sender_ip")
    private String senderIp;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expiredDate;

    // Default Value 설정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.expiredDate = this.createdDate.plusHours(EXPIRY_TIME);
        this.isActive = true;
    }

    @Builder
    public Message(Long id, Heart heart, Emoji emoji, User sender, User receiver, String title, String content, String senderIp) {
        this.id = id;
        this.heart = heart;
        this.emoji = emoji;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.senderIp = senderIp;
    }

    public void deleteMessage() {
        this.isActive = false;
    }

    public void undeleteMessage() {
        this.isActive = true;
    }

    public void reportMessage() {
        this.isReported = true;
    }

    public void updateEmoji(Emoji emoji) {
        this.emoji = emoji;
    }

    public void toInbox() {
        this.isStored = true;
    }

    public void readMessage() {
        this.isRead = true;
    }

}
