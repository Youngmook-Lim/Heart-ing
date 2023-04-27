package com.chillin.hearting.db.domain;

import com.chillin.hearting.exception.ServerLogicException;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockedUser implements Serializable {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
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
        this.startDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    // EndDate 설정
    public void updateEndDate(char status) {
        switch (status) {
            case 'P':
                this.endDate = this.startDate.plusDays(3);
                break;
            case 'O':
                this.endDate = LocalDateTime.of(9999, 12, 31, 0, 0);
                break;
            case 'A':
                throw new ServerLogicException("이 유저는 BlockedUser에 추가되면 안됩니다.");
            default:
                throw new ServerLogicException("이 유저는 이미 탈퇴한 유저입니다.");
        }

    }

}
