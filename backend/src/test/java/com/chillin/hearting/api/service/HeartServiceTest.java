package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.api.data.HeartListData;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeartServiceTest {

    @InjectMocks
    private HeartService heartService;

    @Mock
    private RedisService redisService;

    @Mock
    private HeartRepository heartRepository;

    @Mock
    private UserHeartRepository userHeartRepository;

    private List<Heart> findHearts;
    private List<UserHeart> userHearts;
    private User fakeUser;
    private Heart defaultHeart;
    private Heart defaultAndLockedHeart;
    private Heart specialHeart;
    private Heart notMyHeart;

    private static final String DEFAULT_TYPE = "DEFAULT";
    private static final String SPECIAL_TYPE = "SPECIAL";
    private static final HashSet<Long> lockedHeartSet = new HashSet<>(Arrays.asList(4L, 5L));

    @BeforeEach
    public void 데이터생성() {
        findHearts = new ArrayList<>();
        defaultHeart = createDefaultHeart(1L);
        defaultAndLockedHeart = createDefaultHeart(4L);
        specialHeart = createSpecialHeart(6L);
        notMyHeart = createNotMySpecialHeart(7L);
        findHearts.add(defaultHeart);
        findHearts.add(specialHeart);
        findHearts.add(notMyHeart);

        userHearts = new ArrayList<>();
    }

    @Test
    void 도감조회_비로그인() {

        // given
        fakeUser = null;

        // moking
        when(redisService.getAllHeartInfo(any())).thenReturn(findHearts);

        // when
        HeartListData allHearts = (HeartListData) heartService.findAllHearts(fakeUser);

        // then
        for (HeartData heartData : allHearts.getHeartList()) {
            if (heartData.getType().equals(DEFAULT_TYPE)) {
                assertThat(heartData.getIsLocked()).isEqualTo(false);
            } else if (heartData.getType().equals(SPECIAL_TYPE)) {
                assertThat(heartData.getIsLocked()).isEqualTo(true);
            }
        }
    }

    @Test
    void 도감조회_로그인() {

        // given
        fakeUser = createUser();
        userHearts.add(UserHeart.builder().user(fakeUser).heart(specialHeart).build());

        // mocking
        when(redisService.getAllHeartInfo("ALL")).thenReturn(findHearts);
        when(userHeartRepository.findAllByUserId(any())).thenReturn(userHearts);

        // when
        HeartListData allHearts = (HeartListData) heartService.findAllHearts(fakeUser);

        // then
        for (HeartData heartData : allHearts.getHeartList()) {
            assertThat(heartData.getIsLocked()).isEqualTo((heartData.getType() != "DEFAULT" && heartData.getHeartId() == notMyHeart.getId()) ? true : false);
        }
    }

    @Test
    void 유저하트조회_비로그인() {

        // given
        fakeUser = null;
        List<Heart> defaultHearts = new ArrayList<>();
        defaultHearts.add(defaultHeart);
        defaultHearts.add(defaultAndLockedHeart);

        userHearts.add(UserHeart.builder().user(fakeUser).heart(specialHeart).build());

        // mocking
        when(redisService.getAllHeartInfo(any())).thenReturn(defaultHearts);

        // when
        List<HeartData> heartDataList = heartService.findUserMessageHearts(fakeUser);

        // then
        for (HeartData heartData : heartDataList) {
            if (lockedHeartSet.contains(heartData.getHeartId())) {
                assertThat(heartData.getIsLocked()).isTrue();
            }
        }

    }

    @Test
    void 유저하트조회_로그인() {
        // given
        fakeUser = createUser();
        List<Heart> defaultHearts = new ArrayList<>();
        defaultHearts.add(defaultHeart);
        defaultHearts.add(defaultAndLockedHeart);

        userHearts.add(UserHeart.builder().user(fakeUser).heart(specialHeart).build());

        // mocking
        when(userHeartRepository.findAllByUserIdOrderByHeartId(any())).thenReturn(userHearts);
        when(redisService.getAllHeartInfo(any())).thenReturn(defaultHearts);

        // when
        List<HeartData> heartDataList = heartService.findUserMessageHearts(fakeUser);

        // then
        assertThat(heartDataList).hasSize(3);
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