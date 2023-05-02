package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.InboxData;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.repository.InboxRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.MessageAlreadyExpiredException;
import com.chillin.hearting.exception.MessageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MessageInboxService {

    private final InboxRepository inboxRepository;
    private final UserRepository userRepository;

    @Transactional
    public void storeMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        if (findMessage.getExpiredDate().isBefore(LocalDateTime.now(ZoneId.of("Asia/Seoul"))))
            throw new MessageAlreadyExpiredException();
        findMessage.toInbox();
    }

    @Transactional
    public List<InboxData> findInboxMessages(String userId) {
        List<Message> findMessages = inboxRepository.findAllByReceiverIdAndIsStored(userId, true);
        return findMessages.stream().map(InboxData::of).collect(Collectors.toList());
    }

    @Transactional
    public Message findInboxDetailMessage(String userId, Long messageId) {
        return inboxRepository.findByIdAndReceiverIdAndIsStored(messageId, userId, true).orElseThrow(MessageNotFoundException::new);
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        findMessage.deleteInbox();
        findMessage.deleteMessage();
    }
}
