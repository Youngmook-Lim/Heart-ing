package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // MySQL 사용한다
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void KakaoLoginSuccess(){

        LocalDateTime now = LocalDateTime.now();
        // given
        final User member = User.builder()
                .id("abc")
                .type("KAKAO")
                .email("wjdwn@naver.com")
                .nickname("jj")
                .build();

        // when
        userRepository.save(member);
        final User result = userRepository.findByEmail("wjdwn@naver.com").orElseThrow(NotFoundException::new);

        // then
        assertThat(result.getId()).isEqualTo("abc");
        assertThat(result.getType()).isEqualTo("KAKAO");
        assertThat(result.getEmail()).isEqualTo("wjdwn@naver.com");
        assertThat(result.getNickname()).isEqualTo("jj");
        assertThat(result.getReportedCount()).isEqualTo(0);
        assertThat(result.getRole()).isEqualTo("ROLE_USER");

    }
}
