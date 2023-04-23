package com.chillin.hearting.db.domain;

import com.chillin.hearting.oauth.model.UserType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "\"user\"")
@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

    // pk
    @Id
    @Column(length = 100)
    private String id;

    // 사용자 타입(KAKAO:카카오, GOOGLE:구글)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserType userType;

    // 이메일
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 닉네임
    @Column(unique = true, nullable = false, length = 15)
    private String nickname;

    // refresh토큰
    @Column(name = "refresh_token", unique = true, length = 200)
    private String refreshToken;

    // 가입일시
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // 수정일시
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // 상태메시지
    @Column(name = "status_message", length = 100)
    private String statusMessage;

    // 누적 신고된 횟수
    @Column(name = "reported_count", nullable = false)
    private int reportedCount;

    // 회원상태(A : 활성화, P : 일시정지, O : 영구정지, D : 탈퇴)
    @Column(nullable = false)
    private char status;

    // spring security용 컬럼
    @Column(nullable = false, length = 15)
    private String role;

    // 회원별 메시지 total
    @Column(name = "message_total")
    private Long messageTotal;

    // Default Value 설정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.reportedCount = 0;
        this.status = 'A';
        this.role = "ROLE_USER";
        this.messageTotal = 0L;
    }


    // 카카오 사용자 회원가입
    @Builder
    public User(String id, UserType type, String email, String nickname) {
        this.id = id;
        this.userType = type;
        this.email = email;
        this.nickname = nickname;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    // 프로필 수정
    public void updateUser(String nickname) {
        this.nickname = nickname;
    }

}
