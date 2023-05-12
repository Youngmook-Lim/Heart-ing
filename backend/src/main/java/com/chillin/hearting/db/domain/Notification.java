package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Notification implements Serializable {

    private static final int EXPIRY_TIME = 24;

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heart_id", nullable = false)
    private Heart heart;

    @Column(length = 500, name = "content")
    private String content;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expiredDate;

    @Column(length = 15, name = "type", nullable = false)
    private String type;

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // Default Value 설정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.expiredDate = this.createdDate.plusHours(EXPIRY_TIME);
        this.isActive = true;
    }

    public void deleteNotification() {
        this.isActive = false;
    }

    public void readNotification() {
        this.isChecked = true;
    }
}
