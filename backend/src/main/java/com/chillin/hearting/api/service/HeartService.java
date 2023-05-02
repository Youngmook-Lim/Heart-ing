package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.*;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.MessageHeartConditionRepository;
import com.chillin.hearting.db.repository.UserHeartRepository;
import com.chillin.hearting.exception.HeartNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    private final UserHeartRepository userHeartRepository;
    private final MessageHeartConditionRepository messageHeartConditionRepository;

    private static final String DEFAULT_TYPE = "DEFAULT";
    private static final HashSet<Long> lockedHeartSet = new HashSet<>(Arrays.asList(4L, 5L));
    private static final int HEART_PLANET_MAX_VALUE = 5;
    private static final int HEART_RAINBOW_MAX_VALUE = 1;

    /**
     * 모든 도감 리스트를 반환합니다.
     * DEFAULT 타입의 도감은 잠금이 해제됩니다.
     * 로그인 사용자는 추가적으로 하트 획득 조건을 달성한 하트에 대해 잠금이 해제됩니다.
     *
     * @param user
     * @return 하트 DTO
     */
    public List<HeartData> findAllHearts(User user) {
        log.debug("도감 하트 리스트 조회 - DB의 모든 하트를 조회한다.");
        List<Heart> allHearts = heartRepository.findAll();

        HashSet<Long> hashSet = new HashSet<>();
        if (user != null) {
            String userId = user.getId();
            log.debug("들어온 유저 아이디 : {}", userId);
            List<UserHeart> userHearts = userHeartRepository.findAllByUserId(userId);
            for (UserHeart myHeart : userHearts) {
                hashSet.add(myHeart.getHeart().getId());
            }
        }

        List<HeartData> resHearts = new ArrayList<>();
        for (Heart heart : allHearts) {
            resHearts.add(HeartData.of(heart, (DEFAULT_TYPE.equals(heart.getType()) || hashSet.contains(heart.getId()) ? false : true)));
        }
        return resHearts;
    }

    public List<HeartData> findUserHearts(User user) {
        log.debug("메시지 전송용 하트 리스트 조회 - 기본 하트 + 내가 획득한 하트를 조회한다.");
        List<HeartData> resHearts = new ArrayList<>();
        List<Heart> findHearts = heartRepository.findAllByType(DEFAULT_TYPE);
        for (Heart heart : findHearts) {
            resHearts.add(HeartData.of(heart, false));
        }

        if (user != null) {
            String userId = user.getId();
            log.debug("들어온 유저 아이디 : {}", userId);
            List<UserHeart> myHearts = userHeartRepository.findAllByUserId(userId);
            for (UserHeart myHeart : myHearts) {
                resHearts.add(HeartData.of(myHeart.getHeart(), false));
            }
        } else {
            log.debug("비로그인 유저입니다.");
            for (HeartData heartData : resHearts) {
                if (lockedHeartSet.contains(heartData.getHeartId())) {
                    heartData.setLock();
                }
            }
        }

        return resHearts;
    }

    public Data findHeartDetail(User user, Long heartId) {
        Heart findHeart = heartRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);
        HeartDetailData heartDetailData = HeartDetailData.of(findHeart);

        if (DEFAULT_TYPE.equals(findHeart.getType())) {
            heartDetailData.setIsLocked(false);
        } else {
            if (user != null) {
                String userId = user.getId();
                List<UserHeart> findUserHeart = userHeartRepository.findByHeartIdAndUserId(heartId, userId);
                if (!findUserHeart.isEmpty()) {
                    heartDetailData.setIsLocked(false);
                } else {
                    List<HeartConditionData> conditionList = getHeartCondition(userId, findHeart);
                    heartDetailData.setConditions(conditionList);
                }
            }
        }

        return heartDetailData;
    }

    private List<HeartConditionData> getHeartCondition(String userId, Heart heart) {
        int heartId = heart.getId().intValue();
        List<HeartConditionData> heartConditionList = new ArrayList<>();
        switch (heartId) {
            case 6:
                log.debug("행성 하트 획득 조건을 반환합니다.");

                HeartConditionData heartConditionData = HeartConditionData.of(null);
                int userCurrentValue = messageHeartConditionRepository.findMaxMessageCountToSameUser(userId);
                heartConditionData.setCurrentValue(userCurrentValue);
                heartConditionData.setMaxValue(HEART_PLANET_MAX_VALUE);
                heartConditionList.add(heartConditionData);

            case 7:
                log.debug("무지개 하트 획득 조건을 반환합니다.");
                List<Heart> defaultHearts = heartRepository.findAllByType(DEFAULT_TYPE);
                List<HeartConditionDTO> resultList = heartRepository.findDefaultHeartSentCount(userId);

                for (HeartConditionDTO dto : resultList) {
                    HeartConditionData data = HeartConditionData.builder()
                            .heartId(dto.getHeartId())
                            .name(dto.getName())
                            .heartUrl(dto.getHeartUrl())
                            .currentValue((dto.getCurrentValue() > 1) ? HEART_RAINBOW_MAX_VALUE : dto.getCurrentValue())
                            .maxValue(HEART_RAINBOW_MAX_VALUE)
                            .build();
                    heartConditionList.add(data);
                }
        }

        return heartConditionList;
    }
}
