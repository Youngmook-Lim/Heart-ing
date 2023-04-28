package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report implements Serializable {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    // FK, 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // FK, 신고 당한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private User reportedUser;

    // 신고일시
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // 신고내용
    @Column(nullable = false, length = 500)
    private String content;

    // Default Value 설정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @Builder
    public Report(Message message, User reporter, User reportedUser, String content, Long id) {
        this.message = message;
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.content = content;
        this.id = id;
    }
}
