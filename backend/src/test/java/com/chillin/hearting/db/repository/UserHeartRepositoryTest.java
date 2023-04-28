package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserHeartRepositoryTest {

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHeartRepository userHeartRepository;

    private Heart savedSpecialHeart;
    private User savedUser;

    @BeforeEach
    public void 데이터생성() {
        User user = createUser();
        Heart specialHeart = createSpecialHeart();
        savedUser = userRepository.save(user);
        savedSpecialHeart = heartRepository.save(specialHeart);
    }

    @Test
    public void 유저획득하트조회() {
        // given
        UserHeart userHeart = UserHeart.builder().user(savedUser).heart(savedSpecialHeart).build();
        UserHeart savedUserHeart = userHeartRepository.save(userHeart);

        // when
        List<UserHeart> findUserHeartList = userHeartRepository.findAllByUserId(savedUser.getId());

        // then
        assertThat(findUserHeartList).anySatisfy(uh -> {
            assertThat(uh.getHeart().getType()).isEqualTo("SPECIAL");
        });
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
}
