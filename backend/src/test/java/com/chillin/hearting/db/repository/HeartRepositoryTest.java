package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HeartRepositoryTest {

    @Autowired
    private HeartRepository heartRepository;

    public Heart createHeart() {
        Heart heart = Heart.builder()
                .name("테스트하트")
                .imageUrl("test.com")
                .shortDescription("short test dsc")
                .longDescription("long test dsc")
                .acqCondition("test condition")
                .type("DEFAUT")
                .build();

        return heart;
    }

    @Test
    public void 도감생성() {
        // given
        Heart createHeart = createHeart();

        // when
        Heart savedHeart = heartRepository.save(createHeart);
        Heart findHeart = heartRepository.findById(savedHeart.getId()).orElseThrow(NotFoundException::new);

        // then
        assertThat(findHeart.getId())
                .isEqualTo(savedHeart.getId());
    }

    @Test
    public void 모든도감조회() {

        // given
        Heart createHeart = createHeart();

        // when
        Heart savedHeart = heartRepository.save(createHeart);
        List<Heart> heartList = heartRepository.findAll();

        // then
        assertThat(heartList)
                .anySatisfy(heart -> {
                    assertThat(heart.getName()).isEqualTo(savedHeart.getName());
                });
    }
}
