package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    @Column(nullable = false, length = 15)
    private String type;

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
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.reportedCount = 0;
        this.status = 'A';
        this.role = "ROLE_USER";
        this.statusMessage = "";
        this.messageTotal = 0L;
    }


    // 카카오 사용자 회원가입
    @Builder
    public User(String id, String type, String email, String nickname, Long messageTotal) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.nickname = nickname;
        this.messageTotal = messageTotal;
    }

    // 계정 일시 정지 해제
    public void updateUserStatusToActive(LocalDateTime nowLocalTime) {
        this.updatedDate = nowLocalTime;
        this.status = 'A';
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    // 닉네임 수정
    public void updateNickname(String nickname, LocalDateTime nowLocalTime) {
        this.updatedDate = nowLocalTime;
        this.nickname = nickname;
    }

    // message_total 추가
    public void updateMessageTotal() {
        this.messageTotal++;
    }

    // 상태메시지 수정
    public void updateStatusMessage(String statusMessage, LocalDateTime nowLocalTime) {
        this.updatedDate = nowLocalTime;
        this.statusMessage = statusMessage;
    }

    // 유저 신고
    public void reportUser() {
        this.reportedCount++;
    }

    // 유저 상태 업데이트
    public void updateUserStatus(char status) {
        this.status = status;
    }

}
