package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.UserHeartRepository;
import com.chillin.hearting.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private HeartRepository heartRepository;

    private UserHeartRepository userHeartRepository;

    private UserRepository userRepository;

    private final String DEFAULT_TYPE = "DEFAULT";

    /**
     * 모든 도감 리스트를 반환합니다.
     * DEFAULT 타입의 도감은 잠금이 해제됩니다.
     * 로그인 사용자는 추가적으로 하트 획득 조건을 달성한 하트에 대해 잠금이 해제됩니다.
     *
     * @param user
     * @return 하트 DTO
     */
    public List<HeartData> findAllHearts(User user) {
        // DB의 모든 하트를 조회한다. 기본하트는 isLocked = false
        List<Heart> allHearts = heartRepository.findAll();
        List<HeartData> resHearts = new ArrayList<>();
        for (Heart heart : allHearts) {
            resHearts.add(HeartData.of(heart, (heart.getType() == DEFAULT_TYPE) ? false : true));
        }

        HashSet<Long> hashSet = new HashSet<>();
        if (user != null) {
            String userId = user.getId();
            userRepository.findById(userId);
            List<UserHeart> userHearts = userHeartRepository.findAllByUserId(userId);
            for (UserHeart myHeart : userHearts) {
                hashSet.add(myHeart.getId());
            }

            for (HeartData heartData : resHearts) {
                if (heartData.getType() != DEFAULT_TYPE && hashSet.contains(heartData.getId())) {
                    heartData.unLock();
                }
            }
        }
        
        return resHearts;
    }
}
