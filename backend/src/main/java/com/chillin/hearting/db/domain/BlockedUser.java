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
public class BlockedUser {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 계정 정지 시작 일시
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    // 계정 정지 종료 일시
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    // Default Value 설정
    @PrePersist
    public void prePersist() {
        this.startDate = LocalDateTime.now();
    }

}
