package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.UserHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HeartService {

    private HeartRepository heartRepository;

    private UserHeartRepository userHeartRepository;

    private final String DEFAULT_TYPE = "DEFAULT";

    /**
     * 모든 도감 리스트를 반환합니다.
     * 로그인 사용자는 하트 획득 조건을 달성한 도감에 대해 잠금이 해제됩니다.
     * 비로그인 사용자이면 DEFAULT 타입의 도감만 잠금이 해제됩니다.
     *
     * @param user
     * @return 하트 DTO
     */
    public List<HeartData> findAllHearts(User user) {
        List<Heart> findHearts = heartRepository.findAll();
        List<HeartData> resHearts = new ArrayList<>();
        for (Heart heart : findHearts) {
            resHearts.add(HeartData.builder()
                    .heart(heart)
                    .isLocked((heart.getType() == DEFAULT_TYPE) ? false : true)
                    .build());
        }

        if (user != null) {
            List<UserHeart> userHearts = userHeartRepository.findByUser(user);
            for (HeartData heartData : resHearts) {
                for (UserHeart myHeart : userHearts) {
                    if (heartData.getType() != DEFAULT_TYPE && heartData.getId() == myHeart.getHeart().getId()) {
                        heartData.unLock();
                        break;
                    }
                }
            }
        }

        return resHearts;
    }
}
