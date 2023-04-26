package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.InboxDTO;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.InboxRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.MessageNotFoundException;
import com.chillin.hearting.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InboxService {

    private final InboxRepository inboxRepository;
    private final UserRepository userRepository;

    @Transactional
    public void storeMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        findMessage.toInbox();
    }

    @Transactional
    public List<InboxDTO> findInboxMessages(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Message> findMessages = inboxRepository.findAllByReceiverAndIsStoredAndIsActive(user, true, true);
        List<InboxDTO> inboxList = findMessages.stream().map(message -> InboxDTO.of(message)).collect(Collectors.toList());
        return inboxList;
    }

    @Transactional
    public Message findInboxDetailMessage(String userId, Long messageId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Message findMessage = inboxRepository.findByIdAndIsActive(messageId, true).orElseThrow(MessageNotFoundException::new);
        return findMessage;
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        findMessage.deleteInbox();
    }
}
