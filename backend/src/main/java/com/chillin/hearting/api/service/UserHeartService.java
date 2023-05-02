package com.chillin.hearting.api.service;

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

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserHeartService {

    private final UserHeartRepository userHeartRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;


    public void saveUserHearts(String userId, Long heartId) {
        User findUser = userRepository.findById(userId).get();
        Heart findHeart = heartRepository.findById(heartId).get();
        UserHeart userHeart = new UserHeart(findUser, findHeart);

        userHeartRepository.save(userHeart);
    }
}
