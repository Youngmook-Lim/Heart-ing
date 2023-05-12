package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.*;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Notification;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import com.chillin.hearting.db.repository.*;
import com.chillin.hearting.exception.HeartNotFoundException;
import com.chillin.hearting.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserHeartRepository userHeartRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final MessageHeartConditionRepository messageHeartConditionRepository;
    private final MigrationService migrationService;

    private static final String HEART_TYPE_DEFAULT = "DEFAULT";
    private static final String HEART_TYPE_SPECIAL = "SPECIAL";
    private static final String HEART_TYPE_EVENT = "EVENT";
    private static final String HEART_TYPE_ALL = "ALL";

    private static final int HEART_RAINBOW_MAX_VALUE = 1;
    private static final int HEART_MINCHO_MAX_VALUE = 5;
    private static final int HEART_SUNNY_MAX_VALUE = 5;
    private static final int HEART_READING_GLASSES_MAX_VALUE = 3;
    private static final int HEART_ICECREAM_MAX_VALUE = 3;
    private static final int HEART_SHAMROCK_MAX_VALUE = 3;
    private static final int HEART_FOUR_LEAF_MAX_VALUE = 4;
    private static final int HEART_NOIR_MAX_VALUE = 2;

    private static final HashSet<Long> lockedHeartSet = new HashSet<>(Arrays.asList(4L, 5L));

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_SEND_HEARTS_PREFIX = "userSentHeart:";
    private static final String KEY_RECEIVED_HEARTS_PREFIX = "userReceivedHeart:";
    private static final String KEY_HEART_INFO_PREFIX = "heartInfo:";
    private static final String KEY_HEART_LIST_PREFIX = "heartList:";

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
        log.info("도감 하트 리스트 조회 - DB의 모든 하트를 조회한다.");
        List<Heart> allHearts = getAllHeartInfo(HEART_TYPE_ALL);

        // 유저가 존재한다면, 획득한 하트를 가져옵니다.
        HashSet<Long> myHeartSet = new HashSet<>();
        if (user != null) {
            myHeartSet = findUserHeartIds(user.getId());
            log.info("들어온 유저 아이디 : {} 이미 획득한 스페셜 하트 개수 : {}", user.getId(), myHeartSet.size());
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
     * 유저의 획득 하트 아이디 Set을 반환합니다.
     *
     * @param userId
     * @return
     */
    private HashSet<Long> findUserHeartIds(String userId) {
        HashSet<Long> myHeartSet = new HashSet<>();
        List<UserHeart> userHearts = userHeartRepository.findAllByUserId(userId);
        for (UserHeart myHeart : userHearts) {
            myHeartSet.add(myHeart.getHeart().getId());
        }
        return myHeartSet;
    }

    /**
     * 메시지 전송용 하트 리스트를 조회합니다.
     * 기본 하트 - 모든 잠금이 해제되어있습니다. 비로그인 유저에 한해 두 개의 하트가 잠겨있습니다.
     * 스페셜 하트 - 로그인 유저 중 획득한 스페셜 하트가 제공됩니다.
     *
     * @param user
     * @return
     */
    public List<HeartData> findUserMessageHearts(User user) {
        log.info("메시지 전송용 하트 리스트 조회 - 기본 하트 + 내가 획득한 하트를 조회한다.");
        List<HeartData> resHearts = new ArrayList<>();
        List<Heart> findHearts = getAllHeartInfo(HEART_TYPE_DEFAULT);
        for (Heart heart : findHearts) {
            resHearts.add(HeartData.of(heart, false));
        }

        if (user != null) {
            String userId = user.getId();
            List<UserHeart> myHearts = userHeartRepository.findAllByUserIdOrderByHeartId(userId);
            log.info("들어온 유저 아이디 : {} 이미 획득한 하트 개수 : {}", userId, myHearts.size());
            for (UserHeart myHeart : myHearts) {
                resHearts.add(HeartData.of(myHeart.getHeart(), false));
            }
        } else {
            log.info("비로그인 유저입니다. 특정 하트에 대해 사용을 제한합니다.");
            for (HeartData heartData : resHearts) {
                if (lockedHeartSet.contains(heartData.getHeartId())) {
                    heartData.setLock();
                }
            }
        }
        return resHearts;
    }

    /**
     * REDIS에서 type에 맞는 모든 Heart Info를 찾아 반환한다.
     *
     * @param type
     * @return
     */
    private List<Heart> getAllHeartInfo(String type) {
        log.info("Redis로부터 {}타입의 HeartInfo를 조회합니다.", type);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Heart> result = new ArrayList<>();

        List<Object> heartIdList = listOperations.range(KEY_HEART_LIST_PREFIX + type.toLowerCase(), 0, -1);
        if (heartIdList != null) {
            for (Object heartId : heartIdList) {
                heartId = ((Integer) heartId).longValue();
                Map<String, Object> entries = hashOperations.entries(KEY_HEART_INFO_PREFIX + heartId);
                Heart heart = Heart.builder()
                        .id(((Integer) entries.get("id")).longValue())
                        .name((String) entries.get("name"))
                        .type((String) entries.get("type"))
                        .imageUrl((String) entries.get("imageUrl"))
                        .shortDescription((String) entries.get("shortDescription"))
                        .longDescription((String) entries.get("longDescription"))
                        .acqCondition((String) entries.get("acqCondition"))
                        .build();
                result.add(heart);
            }
        } else {
            log.error("잘못한 하트 타입이 들어왔습니다.");
        }
        return result;
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
                log.info("{}님이 {}번 하트를 상세 조회합니다.", userId, heartId);
                List<UserHeart> findUserHeart = userHeartRepository.findByHeartIdAndUserId(heartId, userId);
                if (!findUserHeart.isEmpty()) {
                    log.info("{}님의 {}번 하트는 이미 획득했습니다", userId, heartId);
                    heartDetailData.setIsLocked(false);
                } else {
                    if (isAcquiredSpecialHeart(userId, heartId, true)) {
                        log.info("{}님의 {}하트는 획득 가능합니다.", userId, heartId);
                        heartDetailData.setIsAcq(true);
                    }
                    List<HeartConditionData> conditionList = heartAcqConditions;
                    heartDetailData.setConditions(conditionList);
                }
            } else {
                log.info("비로그인 유저가 {}번 하트를 상세 조회합니다.", heartId);
            }
        }

        return heartDetailData;
    }

    /**
     * 유저가 획득 가능한 스페셜 하트가 있는지 체크합니다.
     *
     * @param userId
     * @return 알림이 필요한가 ? true : false
     */
    @Transactional
    public boolean hasAcquirableHeart(String userId) {
        boolean isAcq = false;
        // 스페셜 하트 달성 여부 체크
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Object> specialHeartList = listOperations.range(KEY_HEART_LIST_PREFIX + HEART_TYPE_SPECIAL.toLowerCase(), 0, -1);
        HashSet<Long> mySpecialHeartIds = new HashSet<>();
        if (userId != null) {
            mySpecialHeartIds = findUserHeartIds(userId);
        }

        for (Object specialHeartId : specialHeartList) {
            Long hId = ((Integer) specialHeartId).longValue();
            if (!mySpecialHeartIds.contains(hId) && isAcquiredSpecialHeart(userId, hId, false)) {
                ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                String key = "user:" + userId + ":notifiedHeartId:" + hId;
                if (valueOperations.get(key) == null) {
                    saveHeartNotification(userId, hId);
                    valueOperations.set(key, "true", 24L, TimeUnit.HOURS);
                    isAcq = true;
                    log.info("{}번째 하트 획득 가능!! 알림 저장", hId);
                }
            }
        }

        return isAcq;
    }

    /**
     * 획득 가능한 하트가 있을 시, 알림 테이블에 저장한다.
     *
     * @param userId
     * @param hId
     */
    @Transactional
    private void saveHeartNotification(String userId, Long hId) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String heartName = (String) hashOperations.get(KEY_HEART_INFO_PREFIX + hId, "name");
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Heart findHeart = heartRepository.findById(hId).orElseThrow(HeartNotFoundException::new);
        notificationRepository.save(Notification.builder()
                .user(findUser)
                .content(heartName + "하트를 획득할 수 있습니다!")
                .heart(findHeart)
                .type("H")
                .build());
    }

    /**
     * 유저가 보낸 메시지를 바탕으로 보낸 하트 개수를 업데이트합니다.
     *
     * @param userId
     * @param heartId
     */
    public void updateSentHeartCount(String userId, Long heartId) {
        log.info("Redis에 userSentHeart를 업데이트합니다. userId:{} heartId:{}", userId, heartId);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = KEY_SEND_HEARTS_PREFIX + userId;
        // update sent heart count
        if (redisTemplate.hasKey(key)) {
            hashOperations.put(key, heartId.toString(), ((Integer) hashOperations.get(key, heartId.toString())).longValue() + 1);
        } else {
            migrationService.migrateUserSentHeart(userId);
        }
    }

    /**
     * 유저가 받은 메시지를 바탕으로 받은 하트 개수를 업데이트합니다.
     *
     * @param userId
     * @param heartId
     */
    public void updateReceivedHeartCount(String userId, Long heartId) {
        log.info("Redis에 userReceivedHeart를 업데이트합니다. userId:{} heartId:{}", userId, heartId);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = KEY_RECEIVED_HEARTS_PREFIX + userId;
        // update Received heart count
        if (redisTemplate.hasKey(key)) {
            hashOperations.put(key, heartId.toString(), ((Integer) hashOperations.get(key, heartId.toString())).longValue() + 1);
        } else {
            migrationService.migrateUserReceivedHeart(userId);
        }
    }

    /**
     * 스페셜 하트 획득 조건을 충족했는지 확인합니다.
     *
     * @param userId
     * @param heartId
     * @param isSave
     * @return 유저가 해당 하트를 획득 가능한가 ? true : false
     */
    private boolean isAcquiredSpecialHeart(String userId, Long heartId, boolean isSave) {
        log.info("{}번 스페셜 하트 획득 조건을 충족했는지 확인합니다.", heartId);
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        List<Object> defaultHeartList = null;
        Integer currentValue = 0;
        if (isSave) {
            heartAcqConditions = new ArrayList<>();
        }

        boolean isAcquirable = true;
        switch (heartId.intValue()) {
            case 7:
                // 무지개 하트 - 모든 기본하트 1개 보내기
                defaultHeartList = listOperations.range(KEY_HEART_LIST_PREFIX + HEART_TYPE_DEFAULT.toLowerCase(), 0, -1);
                for (Object defaultHeartId : defaultHeartList) {
                    currentValue = (Integer) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, defaultHeartId.toString());
                    if (currentValue < HEART_RAINBOW_MAX_VALUE) {
                        isAcquirable = false;
                        log.info("무지개 하트를 획득 불가 - {}번 하트 조건 미충족", defaultHeartId);
                    }
                    if (isSave) {
                        saveHeartCondition(defaultHeartId, currentValue, HEART_RAINBOW_MAX_VALUE);
                    }
                }
            case 8:
                // 민초 하트 - 파란색 하트 5개 보내기
                String blueHeartId = "2";
                currentValue = (Integer) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, blueHeartId);
                if (currentValue < HEART_MINCHO_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("민초 하트를 획득 불가 - {}번 하트 조건 미충족", blueHeartId);
                }
                if (isSave) {
                    saveHeartCondition(blueHeartId, currentValue, HEART_MINCHO_MAX_VALUE);
                }
                break;
            case 9:
                // 햇살 하트 - 노랑 하트 5개 보내기
                String yellowHeartId = "1";
                currentValue = (Integer) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, yellowHeartId);
                if (currentValue < HEART_SUNNY_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("햇살 하트를 획득 불가 - {}번 하트 조건 미충족", yellowHeartId);
                }
                if (isSave) {
                    saveHeartCondition(yellowHeartId, currentValue, HEART_SUNNY_MAX_VALUE);
                }
                break;
            case 10:
                // 돋보기 하트 - 특정인에게 핑크 하트 3개 보내기
                String pinkHeartId = "4";
                currentValue = messageHeartConditionRepository.findMaxMessageCountToSameUser(userId, Long.parseLong(pinkHeartId));
                if (currentValue == null) currentValue = 0;
                if (currentValue < HEART_READING_GLASSES_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("돋보기 하트를 획득 불가 - {}번 하트 조건 미충족", pinkHeartId);
                }
                if (isSave) {
                    saveHeartCondition(pinkHeartId, currentValue, HEART_READING_GLASSES_MAX_VALUE);
                }
                break;
            case 11:
                // 아이스크림 하트  - 햇살 하트 3개 받기
                String sunnyHeartId = "9";
                currentValue = (Integer) hashOperations.get(KEY_RECEIVED_HEARTS_PREFIX + userId, sunnyHeartId);
                if (currentValue < HEART_ICECREAM_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("아이스크림 하트를 획득 불가 - {}번 하트 조건 미충족", sunnyHeartId);
                }
                if (isSave) {
                    saveHeartCondition(sunnyHeartId, currentValue, HEART_ICECREAM_MAX_VALUE);
                }
                break;
            case 12:
                // 세잎클로버 하트 - 초록 하트 3개 보내기
                String greenHeartId = "3";
                currentValue = (Integer) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, greenHeartId);
                if (currentValue < HEART_SHAMROCK_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("세잎클로버 하트를 획득 불가 - {}번 하트 조건 미충족", greenHeartId);
                }
                if (isSave) {
                    saveHeartCondition(greenHeartId, currentValue, HEART_SHAMROCK_MAX_VALUE);
                }
                break;
            case 13:
                // 네잎클로버 하트 - 세잎클로버 하트 4개 받기
                String shamrockHeartId = "12";
                currentValue = (Integer) hashOperations.get(KEY_RECEIVED_HEARTS_PREFIX + userId, shamrockHeartId);
                if (currentValue < HEART_FOUR_LEAF_MAX_VALUE) {
                    isAcquirable = false;
                    log.info("네잎클로버 하트를 획득 불가 - {}번 하트 조건 미충족", shamrockHeartId);
                }
                if (isSave) {
                    saveHeartCondition(shamrockHeartId, currentValue, HEART_FOUR_LEAF_MAX_VALUE);
                }
                break;
            case 14:
                // 질투의 누아르 하트 - 모든 기본하트 2개 보내기
                defaultHeartList = listOperations.range(KEY_HEART_LIST_PREFIX + HEART_TYPE_DEFAULT.toLowerCase(), 0, -1);
                for (Object defaultHeartId : defaultHeartList) {
                    currentValue = (Integer) hashOperations.get(KEY_SEND_HEARTS_PREFIX + userId, defaultHeartId.toString());
                    if (currentValue < HEART_NOIR_MAX_VALUE) {
                        isAcquirable = false;
                        log.info("누아르 하트를 획득 불가 - {}번 하트 조건 미충족", defaultHeartId);
                    }
//                    if (isSave) {
//                        saveHeartCondition(defaultHeartId, currentValue, HEART_NOIR_MAX_VALUE);
//                    }
                }
                break;
        }
        return isAcquirable;
    }

    /**
     * 특정 하트 획득 상세 조건을 전역 변수에 저장한다.
     *
     * @param defaultHeartId
     * @param currentValue
     * @param maxValue
     */
    private void saveHeartCondition(Object defaultHeartId, Integer currentValue, Integer maxValue) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String heartKey = KEY_HEART_INFO_PREFIX + defaultHeartId;
        heartAcqConditions.add(HeartConditionData.builder()
                .heartId(((Integer) hashOperations.get(heartKey, "id")).longValue())
                .name((String) hashOperations.get(heartKey, "name"))
                .heartUrl((String) hashOperations.get(heartKey, "imageUrl"))
                .currentValue(currentValue.longValue())
                .maxValue(maxValue.longValue())
                .build()
        );
    }
}
