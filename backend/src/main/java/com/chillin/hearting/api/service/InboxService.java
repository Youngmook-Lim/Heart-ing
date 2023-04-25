package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.InboxData;
import com.chillin.hearting.api.data.InboxDetailData;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InboxService {

    private final InboxRepository inboxRepository;
    private final UserRepository userRepository;

    public void storeMessage(Long messageId) {
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        findMessage.toInbox();
    }

    public InboxData findInboxMessages(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return InboxData.builder().inboxList(inboxRepository.findAllByReceiverAndIsStored(user, true)).build();
    }

    public InboxDetailData findInboxDetailMessage(String userId, Long messageId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Message findMessage = inboxRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        return InboxDetailData.builder().message(findMessage).build();
    }
}
