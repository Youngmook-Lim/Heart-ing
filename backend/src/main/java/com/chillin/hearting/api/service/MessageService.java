package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.MessageRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.*;
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
    private final HeartRepository heartRepository;

    @Transactional
    public MessageData sendMessage(long heartId, String senderId, String receiverId, String title, String content, String senderIp) {

        // Check if receiver exists
        User receiver = userRepository.findById(receiverId).orElseThrow(UserNotFoundException::new);
        // Add cnt to receiver's messageTotal
        receiver.updateMessageTotal();
        userRepository.save(receiver);

        // Check if sender exists (if logged in user)
        User sender = senderId == null ? null : userRepository.findById(senderId).orElseThrow(UserNotFoundException::new);

        // Check if heart exists
        Heart heart = heartRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);

        // Create message
        Message message = Message.builder().heart(heart).receiver(receiver).sender(sender).title(title).content(content).senderIp(senderIp).build();
        message = messageRepository.save(message);

        return MessageData.builder()
                .messageId(message.getId())
                .heartId(message.getHeart().getId())
                .heartName(message.getHeart().getName())
                .heartUrl(message.getHeart().getImageUrl())
                .isRead(message.isRead()).build();
    }

    @Transactional
    public boolean deleteMessage(long messageId, String userId) {

        // Check if messageId exists
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);

        // Check if userId matches the receiverId
        if (!message.getReceiver().getId().equals(userId)) {
            throw new UnAuthorizedException();
        }

        // Check if already deleted
        if (!message.isActive()) {
            throw new MessageAlreadyDeletedException();
        }

        message.deleteMessage();

        message = messageRepository.save(message);

        return message.isActive();
    }

}

