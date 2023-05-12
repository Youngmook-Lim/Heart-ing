package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.HeartCountDTO;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MigrationService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_SEND_HEARTS_PREFIX = "userSentHeart:";
    private static final String KEY_RECEIVED_HEARTS_PREFIX = "userReceivedHeart:";

    /**
     * MySQL에 저장된 모든 하트 정보를 Redis에 업데이트 합니다.
     */
    public void migrateHeartInfo() {
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

        log.info("모든 Heart Info 마이그레이션에 성공했습니다.");
    }

    /**
     * MySQL의 모든 유저에 대해 User Sent Heart 수를 Redis에 업데이트 합니다.
     */
    public void migrateAllUserSentHeart() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            migrateUserSentHeart(user.getId());
        }

        log.info("{}명의 유저에 대해 User Sent Heart 마이그레이션에 성공했습니다.", userList.size());
    }

    /**
     * 한 명의 유저에 대해 User Sent Heart 수를 Redis에 업데이트 합니다.
     *
     * @param userId
     */
    public void migrateUserSentHeart(String userId) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        String keyPrefix = KEY_SEND_HEARTS_PREFIX;
        List<HeartCountDTO> heartCountDTOList = heartRepository.findAllHeartSentCount(userId);
        for (HeartCountDTO dto : heartCountDTOList) {
            hashOperations.put(keyPrefix + userId, dto.getHeartId().toString(), dto.getCurrentValue());
        }
    }

    /**
     * MySQL의 모든 유저에 대해 User Received Heart 수를 Redis에 업데이트 합니다.
     */
    public void migrateAllUserReceivedHeart() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            migrateUserReceivedHeart(user.getId());
        }

        log.info("{}명의 유저에 대해 User Received Heart 마이그레이션에 성공했습니다.", userList.size());
    }

    /**
     * 한 명의 유저에 대해 User Received Heart 수를 Redis에 업데이트 합니다.
     *
     * @param userId
     */
    public void migrateUserReceivedHeart(String userId) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        String keyPrefix = KEY_RECEIVED_HEARTS_PREFIX;
        List<HeartCountDTO> heartCountDTOList = heartRepository.findAllHeartReceivedCount(userId);
        for (HeartCountDTO dto : heartCountDTOList) {
            hashOperations.put(keyPrefix + userId, dto.getHeartId().toString(), dto.getCurrentValue());
        }
    }
}
