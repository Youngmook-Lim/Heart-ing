package com.chillin.hearting.db.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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
    @Column(unique = true, length = 200, name = "refresh_token")
    private String refreshToken;

    // 가입일시
    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate;

    // 수정일시
    @Column(nullable = false, name = "updated_date")
    private LocalDateTime updatedDate;

    // 누적 신고된 횟수
    @Column(nullable = false, name = "report_count")
    private int reportCount;

    // 회원상태(A : 활성화, P : 일시정지, O : 영구정지, D : 탈퇴)
    @Column(nullable = false)
    private char status;

    // spring security용 컬럼
    @Column(nullable = false, length = 15)
    private String role;

    // 회원별 미니하트 total
    @Column(nullable = false, name = "miniheart_total")
    private Long miniheartTotal;

    // 회원별 미니하트 today
    @Column(nullable = false, name = "miniheart_today")
    private Long miniheartToday;

    // 회원별 메시지 total
    @Column(name = "message_total")
    private Long messageTotal;


    // 카카오 사용자 회원가입
//    public User(String email, String nickname) {
//        this.email = email;
//        this.nickname = nickname;
//        this.type = "KAKAO";
//        this.role = "ROLE_USER";
//    }

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
