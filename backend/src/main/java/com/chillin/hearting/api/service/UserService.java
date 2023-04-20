package com.chillin.hearting.api.service;

import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j // log를 사용하기 위한 어노테이션
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 닉네임 중복체크
    public void duplicateNickname(String nickname) throws DuplicateException {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateException("중복된 닉네임입니다.");
        }

    }
}
