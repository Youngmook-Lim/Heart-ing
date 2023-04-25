package com.chillin.hearting.api.service;

import com.chillin.hearting.api.response.HeartRes;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.UserHeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeartServiceTest {

    @InjectMocks
    private HeartService heartService;

    @Mock
    private HeartRepository heartRepository;

    @Mock
    private UserHeartRepository userHeartRepository;

    private List<Heart> findHearts;
    private List<UserHeart> userHearts;
    private Heart defaultHeart;
    private Heart specialHeart;
    private Heart notMyHeart;

    @BeforeEach
    public void 데이터생성() {
        findHearts = new ArrayList<>();
        defaultHeart = createDefaultHeart(1L);
        specialHeart = createSpecialHeart(2L);
        notMyHeart = createNotMySpecialHeart(3L);
        findHearts.add(defaultHeart);
        findHearts.add(specialHeart);
        findHearts.add(notMyHeart);
    }

    @Test
    public void 비로그인유저도감조회() {

        // given
        User fakeUser = null;

        // moking
        when(heartRepository.findAll()).thenReturn(findHearts);

        // when
        List<Heart> savedHearts = heartRepository.findAll();
        List<HeartRes> allHearts = heartService.findAllHearts(fakeUser);

        // then
        for (HeartRes heartRes : allHearts) {
            assertThat(heartRes.getIsLocked()).isEqualTo((heartRes.getType() == "DEFAULT") ? false : true);
        }
    }

    @Test
    public void 로그인유저도감조회() {

        // given
        User fakeUser = createUser();
        userHearts = new ArrayList<>();
        userHearts.add(UserHeart.builder().user(fakeUser).heart(specialHeart).build());

        // mocking
        when(heartRepository.findAll()).thenReturn(findHearts);
        when(userHeartRepository.findByUser(fakeUser)).thenReturn(userHearts);

        // when
        List<HeartRes> allHearts = heartService.findAllHearts(fakeUser);

        // then
        for (HeartRes heartRes : allHearts) {
            assertThat(heartRes.getIsLocked()).isEqualTo((heartRes.getType() != "DEFAULT" && heartRes.getId() == notMyHeart.getId()) ? true : false);
        }
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

    public Heart createDefaultHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("호감 하트")
                .imageUrl("test.com")
                .shortDescription("짧은 설명 !")
                .longDescription("호감의 탄생 스토리")
                .acqCondition("기본 제공")
                .type("DEFAULT")
                .build();

        return heart;
    }

    public Heart createSpecialHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("행성 하트")
                .imageUrl("universe.com")
                .shortDescription("우주에 단 하나 뿐인 너 !")
                .longDescription("우주의 탄생 스토리")
                .acqCondition("특정인에게 5회 이상 메시지 전송")
                .type("SPECIAL")
                .build();

        return heart;
    }

    public Heart createNotMySpecialHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("민초 하트")
                .imageUrl("mint-choco.com")
                .shortDescription("mint !")
                .longDescription("mint story")
                .acqCondition("mint mint !!")
                .type("SPECIAL")
                .build();

        return heart;
    }


}