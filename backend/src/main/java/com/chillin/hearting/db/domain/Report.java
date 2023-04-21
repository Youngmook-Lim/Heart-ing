package com.chillin.hearting.db.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    // FK, 신고자
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // FK, 신고 당한 사람
    @ManyToOne(fetch =  FetchType.LAZY)
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
        this.createdDate = LocalDateTime.now();
    }
}
