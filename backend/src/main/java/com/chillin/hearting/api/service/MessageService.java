package com.chillin.hearting.api.service;

import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.MessageRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendMessage(long heartId, String senderId, String receiverId, String content) {

        // Check if receiver exists
        User receiver = userRepository.findById(receiverId).orElseThrow(UserNotFoundException::new);
    }

}

