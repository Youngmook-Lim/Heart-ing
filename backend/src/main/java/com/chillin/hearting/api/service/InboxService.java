package com.chillin.hearting.api.service;

import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.repository.InboxRepository;
import com.chillin.hearting.exception.MessageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InboxService {

    private InboxRepository inboxRepository;

    public void storeMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        findMessage.toInbox();
    }
}
