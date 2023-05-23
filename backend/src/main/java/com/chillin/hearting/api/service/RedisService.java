package com.chillin.hearting.api.service;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Notification;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.NotificationRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.HeartNotFoundException;
import com.chillin.hearting.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final HeartRepository heartRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final MigrationService migrationService;
    private final RedisTemplate<String, Object> redisTemplate;

    public static final String KEY_HEART_INFO_PREFIX = "heartInfo:";
    public static final String KEY_HEART_LIST_PREFIX = "heartList:";
    public static final String KEY_SEND_HEARTS_PREFIX = "userSentHeart:";
    public static final String KEY_RECEIVED_HEARTS_PREFIX = "userReceivedHeart:";
    public static final String HEART_TYPE_SPECIAL = "SPECIAL";
    public static final String HEART_TYPE_DEFAULT = "DEFAULT";


    /**
     * REDIS에서 type에 맞는 모든 Heart Info를 찾아 반환한다.
     *
     * @param type
     * @return
     */
    public List<Heart> getAllHeartInfo(String type) {
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
     * Redis에서 Special Heart 리스트를 반환합니다.
     *
     * @return
     */
    @Cacheable(value = "specialList", cacheManager = "redisCacheManager")
    public List<Object> getSpecialHeartList() {
        return redisTemplate.opsForList().range(KEY_HEART_LIST_PREFIX + HEART_TYPE_SPECIAL.toLowerCase(), 0, -1);
    }

    /**
     * Redis에서 Default Heart 리스트를 반환합니다.
     *
     * @return
     */
    @Cacheable(value = "defaultList", cacheManager = "redisCacheManager")
    public List<String> getDefaultHeartList() {
        return redisTemplate.opsForList().range(KEY_HEART_LIST_PREFIX + HEART_TYPE_DEFAULT.toLowerCase(), 0, -1).stream().map(o -> o.toString()).collect(Collectors.toList());
    }

    @Cacheable(value = "allList", cacheManager = "redisCacheManager")
    public List<Object> getAllHeartList() {
        return redisTemplate.opsForList().range(KEY_HEART_LIST_PREFIX + "all", 0, -1);
    }

    /**
     * Redis에 저장된 알림이 있는지 체크합니다.
     *
     * @param key
     * @return
     */
    public boolean hasNotification(String key) {
        return (redisTemplate.opsForValue().get(key) != null) ? true : false;
    }

    /**
     * 획득 가능한 하트가 있을 시, 알림 테이블에 저장한다.
     *
     * @param userId
     * @param hId
     */
    @Transactional
    public void saveHeartNotification(String userId, Long hId) {
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

    public void setNotification(String key) {
        redisTemplate.opsForValue().set(key, "true", 24L, TimeUnit.HOURS);
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
     * Redis Hash에서 value를 조회합니다.
     *
     * @param key
     * @param field
     * @return
     */

//    @Cacheable(value = "user", key = "#key+ ':' + #field", cacheManager = "redisCacheManager")
    public Object get(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Cacheable(value = "info", key = "'heartInfo:' + #key", cacheManager = "redisCacheManager")
    public Heart getHeartInfo(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        Map<String, Object> entries = hashOperations.entries(KEY_HEART_INFO_PREFIX + key);
        Heart heart = Heart.builder()
                .id(((Integer) entries.get("id")).longValue())
                .name((String) entries.get("name"))
                .type((String) entries.get("type"))
                .imageUrl((String) entries.get("imageUrl"))
                .shortDescription((String) entries.get("shortDescription"))
                .longDescription((String) entries.get("longDescription"))
                .acqCondition((String) entries.get("acqCondition"))
                .build();
        return heart;
    }

    @Cacheable(value = "allHearts", cacheManager = "redisCacheManager")
    public List<Heart> getAllHearts() {
        List<Heart> allHearts = new ArrayList<>();
        for (Object heartId : getAllHeartList()) {
            allHearts.add(getHeartInfo(heartId.toString()));
        }
        return allHearts;
    }
}
