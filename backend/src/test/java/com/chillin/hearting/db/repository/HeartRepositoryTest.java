package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HeartRepositoryTest {

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private UserRepository userRepository;

    private Heart savedDefaultHeart;
    private Heart savedSpecialHeart;
    private User savedUser;

    private final static String DEFAULT_TYPE = "DEFAULT";

    @BeforeEach
    void 데이터생성() {
        Heart defaultHeart = createDefaultHeart();
        Heart specialHeart = createSpecialHeart();
        User user = createUser();

        savedDefaultHeart = heartRepository.save(defaultHeart);
        savedSpecialHeart = heartRepository.save(specialHeart);
        savedUser = userRepository.save(user);
    }


    @Test
    void 모든도감조회() {
        // when
        List<Heart> heartList = heartRepository.findAll();

        // then
        assertThat(heartList)
                .anySatisfy(heart -> {
                    assertThat(heart.getName()).isEqualTo(savedDefaultHeart.getName());
                });

        assertThat(heartList).extracting("type")
                .containsOnly("DEFAULT", "SPECIAL");
    }

    @Test
    void 기본하트조회() {
        // when
        List<Heart> heartList = heartRepository.findAllByType("SPECIAL");

        // then
        System.out.println(heartList.size());
        assertThat(heartList)
                .allSatisfy(heart -> {
                    assertThat(heart.getType().equals(DEFAULT_TYPE));
                    System.out.println(heart.getName());
                });
    }

    public Heart createDefaultHeart() {
        Heart heart = Heart.builder()
                .name("호감 하트")
                .imageUrl("test.com")
                .shortDescription("짧은 설명 !")
                .longDescription("호감의 탄생 스토리")
                .acqCondition("기본 제공")
                .type("DEFAULT")
                .build();

        return heart;
    }

    public Heart createSpecialHeart() {
        Heart heart = Heart.builder()
                .name("행성 하트")
                .imageUrl("universe.com")
                .shortDescription("우주에 단 하나 뿐인 너 !")
                .longDescription("우주의 탄생 스토리")
                .acqCondition("특정인에게 5회 이상 메시지 전송")
                .type("SPECIAL")
                .build();

        return heart;
    }

    public User createUser() {
        User user = User.builder()
                .id("test123")
                .type("ROLE_TEST")
                .email("test-email.com")
                .nickname("test-nick")
                .build();
        return user;
    }
}
