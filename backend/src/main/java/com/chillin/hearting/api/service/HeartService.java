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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserHeartRepository userHeartRepository;
    private final MessageHeartConditionRepository messageHeartConditionRepository;

    private static final String HEART_TYPE_DEFAULT = "DEFAULT";
    private static final String HEART_TYPE_SPECIAL = "SPECIAL";
    private static final String HEART_TYPE_EVENT = "EVENT";
    private static final int HEART_PLANET_MAX_VALUE = 5;
    private static final int HEART_RAINBOW_MAX_VALUE = 1;
    private static final HashSet<Long> lockedHeartSet = new HashSet<>(Arrays.asList(4L, 5L));
    private static final ArrayList<Long> defaultHeartList = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L));
    private static final ArrayList<Long> specialHeartList = new ArrayList<>(Arrays.asList(7L));

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_SEND_HEARTS_PREFIX = "userSentHeart:";

    private ArrayList<HeartConditionData> heartAcqConditions;

    /**
     * 모든 도감 리스트를 반환합니다.
     * DEFAULT 타입의 도감은 잠금이 해제됩니다.
     * 로그인 사용자는 추가적으로 하트 획득 조건을 달성한 하트에 대해 잠금이 해제됩니다.
     *
     * @param user
     * @return 하트 DTO
     */
    public Data findAllHearts(User user) {
        log.debug("도감 하트 리스트 조회 - DB의 모든 하트를 조회한다.");
        List<Heart> allHearts = heartRepository.findAll();

        // 유저가 존재한다면, 획득한 하트를 가져옵니다.
        HashSet<Long> myHeartSet = new HashSet<>();
        if (user != null) {
            String userId = user.getId();
            log.debug("들어온 유저 아이디 : {}", userId);
            List<UserHeart> userHearts = userHeartRepository.findAllByUserId(userId);
            for (UserHeart myHeart : userHearts) {
                myHeartSet.add(myHeart.getHeart().getId());
            }
        }

        // 모든 하트를 반환하되, 기본 하트이거나 내가 획득한 하트는 잠금이 해제됩니다. 아직 잠긴 하트 중 내가 획득할 수 있는 하트인지 체크합니다.
        List<HeartData> resHearts = new ArrayList<>();
        for (Heart heart : allHearts) {
            HeartData heartData = HeartData.of(heart, (HEART_TYPE_DEFAULT.equals(heart.getType()) || myHeartSet.contains(heart.getId()) ? false : true));
            if (user != null && heartData.getIsLocked())
                heartData.setAcq(isAcquiredSpecialHeart(user.getId(), heart.getId(), false));
            resHearts.add(heartData);
        }
        return HeartListData.builder().heartList(resHearts).build();
    }

    /**
     * 메시지 전송용 하트 리스트를 조회합니다.
     * 기본 하트 - 모든 잠금이 해제되어있습니다. 비로그인 유저에 한해 두 개의 하트가 잠겨있습니다.
     * 스페셜 하트 - 로그인 유저 중 획득한 스페셜 하트가 제공됩니다.
     *
     * @param user
     * @return
     */
    public List<HeartData> findUserHearts(User user) {
        log.debug("메시지 전송용 하트 리스트 조회 - 기본 하트 + 내가 획득한 하트를 조회한다.");
        List<HeartData> resHearts = new ArrayList<>();
        List<Heart> findHearts = heartRepository.findAllByType(HEART_TYPE_DEFAULT);
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
            log.debug("비로그인 유저입니다. 특정 하트에 대해 사용을 제한합니다.");
            for (HeartData heartData : resHearts) {
                if (lockedHeartSet.contains(heartData.getHeartId())) {
                    heartData.setLock();
                }
            }
        }
        return resHearts;
    }

    /**
     * 도감 하트 상세보기
     * 특정 하트에 대해 하트 정보, 획득 조건에 대한 정보를 제공한다.
     * 기본 하트 - 잠금이 해제되어 있다.
     * 스페셜 하트 - 로그인 유저가 획득한 하트에 대해 잠금이 해제되어 있다. 획득하지 못한 하트에 대해서는 달성 현황 정보를 제공한다.
     *
     * @param user
     * @param heartId
     * @return
     */
    public Data findHeartDetail(User user, Long heartId) {
        Heart findHeart = heartRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);
        HeartDetailData heartDetailData = HeartDetailData.of(findHeart);

        if (HEART_TYPE_DEFAULT.equals(findHeart.getType())) {
            heartDetailData.setIsLocked(false);
        } else if (HEART_TYPE_SPECIAL.equals(findHeart.getType()) || HEART_TYPE_EVENT.equals(findHeart.getType())) {
            if (user != null) {
                String userId = user.getId();
                List<UserHeart> findUserHeart = userHeartRepository.findByHeartIdAndUserId(heartId, userId);
                if (!findUserHeart.isEmpty()) {
                    log.debug("{}님의 {}번 하트는 이미 획득했습니다", userId, heartId);
                    heartDetailData.setIsLocked(false);
                } else {
                    if (isAcquiredSpecialHeart(userId, heartId, true)) {
                        log.debug("{}님의 {}하트는 획득 가능합니다.");
                        heartDetailData.setIsAcq(true);
                    }
                    List<HeartConditionData> conditionList = heartAcqConditions;
                    heartDetailData.setConditions(conditionList);
                }
            }
        }

        return heartDetailData;
    }

    /**
     * 유저의 보낸 하트 개수를 업데이트하고 스페셜 하트 획득 조건을 만족하는지 체크합니다.
     *
     * @param userId
     * @param heartId
     */
    public void updateHeartCondition(String userId, Long heartId) {

        // 하트 개수 업데이트
        updateHeartCount(userId, heartId);
        // 스페셜 하트 달성 여부 체크
        for (Long specialHeartId : specialHeartList) {
            isAcquiredSpecialHeart(userId, specialHeartId, false);
        }
    }

    /**
     * 유저가 보낸 메시지를 바탕으로 보낸 하트 개수를 업데이트합니다.
     *
     * @param userId
     * @param heartId
     */
    private void updateHeartCount(String userId, Long heartId) {
        log.debug("유저가 보낸 메시지를 바탕으로 보낸 하트 개수를 업데이트합니다.");
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();

        String key = KEY_SEND_HEARTS_PREFIX + userId;
        // update sent heart count
        if (redisTemplate.hasKey(key)) {
            System.out.println(hashOperations.get(key, heartId.toString()));
            hashOperations.put(key, heartId.toString(), hashOperations.get(key, heartId.toString()) + 1);
        } else {
            List<HeartCountDTO> heartCountDTOList = heartRepository.findAllHeartSentCount(userId);
            for (HeartCountDTO dto : heartCountDTOList) {
                hashOperations.put(key, dto.getHeartId().toString(), dto.getCurrentValue());
            }
        }
    }

    /**
     * 스페셜 하트 획득 조건을 충족했는지 확인합니다.
     *
     * @param userId
     * @param heartId
     * @param isSave
     * @return
     */
    private boolean isAcquiredSpecialHeart(String userId, Long heartId, boolean isSave) {
        log.debug("스페셜 하트 획득 조건을 충족했는지 확인합니다. {}", heartId);
        if (isSave) {
            heartAcqConditions = new ArrayList<>();
        }

        boolean isAcquirable = false;
        switch (heartId.intValue()) {
            case 6:
                log.debug("행성 하트 획득 조건 확인.");
//                int userCurrentValue = messageHeartConditionRepository.findMaxMessageCountToSameUser(userId);
                break;

            case 7:
                log.debug("무지개 하트 획득 조건 확인");
                isAcquirable = true;
                HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
                for (Long defaultHeartId : defaultHeartList) {
                    if ((long) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, defaultHeartId.toString()) < HEART_RAINBOW_MAX_VALUE) {
                        isAcquirable = false;
                        log.debug("무지개 하트를 획득할 수 없습니다.");
                    }
                    if (isSave) {
                        String heartKey = "heartInfo:" + defaultHeartId;
                        heartAcqConditions.add(HeartConditionData.builder()
                                .heartId((long) hashOperations.get(heartKey, "id"))
                                .name((String) hashOperations.get(heartKey, "name"))
                                .heartUrl((String) hashOperations.get(heartKey, "imageUrl"))
                                .currentValue((long) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, defaultHeartId.toString()))
                                .maxValue((long) HEART_RAINBOW_MAX_VALUE)
                                .build()
                        );
                    }
                }
        }
        return isAcquirable;
    }

    /**
     * MySQL에 저장된 모든 하트 정보를 Redis에 업데이트 합니다.
     */
    public void synchronizeHeartInfo() {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        Map<String, Object> map;
        for (Heart heart : heartRepository.findAll()) {
            map = new HashMap<>();
            map.put("id", heart.getId());
            map.put("name", heart.getName());
            map.put("imageUrl", heart.getImageUrl());
            map.put("shortDescription", heart.getShortDescription());
            map.put("longDescription", heart.getLongDescription());
            map.put("type", heart.getType());
            map.put("acqCondition", heart.getAcqCondition());
            hashOperations.putAll("heartInfo:" + heart.getId(), map);
        }

    }
}
