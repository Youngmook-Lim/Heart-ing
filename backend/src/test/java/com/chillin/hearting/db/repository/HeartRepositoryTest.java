package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
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

    @Test
    public void 도감저장() {
        Heart heart = Heart.builder()
                .name("테스트하트")
                .imageUrl("test.com")
                .shortDescription("short test dsc")
                .longDescription("long test dsc")
                .acqCondition("test condition")
                .type("DEFAUT")
                .build();

        heartRepository.save(heart);
    }

    @Test
    public void 모든도감조회() {
        List<Heart> heartList = heartRepository.findAll();
        assertThat(heartList)
                .anySatisfy(heart -> {
                    assertThat(heart.getName()).isEqualTo("테스트하트");
                });
    }
}
