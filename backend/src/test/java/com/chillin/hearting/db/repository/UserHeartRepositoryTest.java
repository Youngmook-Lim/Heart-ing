package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
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

    @Test
    public void 기본하트저장() {
        // given
        List<Heart> resultList = createDefaultHearts();
        User createUser = createUser();

        // when
        User findUser = userRepository.save(createUser);
        List<Heart> findHeartList = heartRepository.saveAll(resultList);

        List<UserHeart> userHeartList = new ArrayList<>();
        for (Heart defaultHeart : findHeartList) {
            userHeartList.add(UserHeart.builder().user(findUser).heart(defaultHeart).build());
        }
        List<UserHeart> findUserHeartList = userHeartRepository.saveAll(userHeartList);

        // then
        assertThat(findUserHeartList)
                .isEqualTo(findHeartList.size());
    }

    public List<Heart> createDefaultHearts() {
        List<Heart> resultList = new ArrayList<>();

        Heart heart1 = Heart.builder()
                .name("호감 하트")
                .imageUrl("yellow.com")
                .shortDescription("yellow")
                .longDescription("yellowyellow")
                .acqCondition("yellow condition")
                .type("DEFAUT")
                .build();

        Heart heart2 = Heart.builder()
                .name("응원 하트")
                .imageUrl("blue.com")
                .shortDescription("blue")
                .longDescription("blueblue")
                .acqCondition("blue condition")
                .type("DEFAUT")
                .build();

        Heart heart3 = Heart.builder()
                .name("우정 하트")
                .imageUrl("green.com")
                .shortDescription("green")
                .longDescription("greengreen")
                .acqCondition("green condition")
                .type("DEFAUT")
                .build();

        Heart heart4 = Heart.builder()
                .name("설렘 하트")
                .imageUrl("pink.com")
                .shortDescription("pink")
                .longDescription("pinkpink")
                .acqCondition("pink condition")
                .type("DEFAUT")
                .build();

        Heart heart5 = Heart.builder()
                .name("애정 하트")
                .imageUrl("red.com")
                .shortDescription("red")
                .longDescription("redred")
                .acqCondition("red condition")
                .type("DEFAUT")
                .build();

        resultList.add(heart1);
        resultList.add(heart2);
        resultList.add(heart3);
        resultList.add(heart4);
        resultList.add(heart5);

        return resultList;
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
