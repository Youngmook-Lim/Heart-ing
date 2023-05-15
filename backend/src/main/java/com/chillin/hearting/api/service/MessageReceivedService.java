package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.api.data.ReceivedMessageData;
import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.repository.MessageRepository;
import com.chillin.hearting.exception.MessageNotFoundException;
import com.chillin.hearting.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageReceivedService {

    private final MessageRepository messageRepository;

    @Transactional
    public ReceivedMessageData getReceivedMessages(String userId, boolean isSelf) {

        // Get list of all received messages
        List<Message> initialList = messageRepository.findByReceiverIdAndIsActiveTrue(userId, Sort.by(Sort.Direction.DESC, "createdDate"));

        // Update expired messages
        ReceivedMessageData receivedMessageData = ReceivedMessageData.builder()
                .messageList(new ArrayList<>()).build();

        for (Message m : initialList) {
            processMessage(m, receivedMessageData, isSelf);
        }

        log.info(userId + " 유저가 메시지 리스트를 조회했습니다. 총 " + receivedMessageData.getMessageList().size() + "개의 메시지가 조회되었습니다.");

        return receivedMessageData;
    }

    private void processMessage(Message m, ReceivedMessageData receivedMessageData, boolean isSelf) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime expiredDate = m.getExpiredDate();
        if (expiredDate.isAfter(now)) {
            // Not yet expired
            // Create MessageData
            Heart heart = m.getHeart();
            Emoji emoji = m.getEmoji();
            long emojiId = emoji != null ? emoji.getId() : -1;
            String emojiName = emoji != null ? emoji.getName() : null;
            String emojiUrl = emoji != null ? emoji.getImageUrl() : null;
            MessageData messageData = MessageData.builder()
                    .messageId(m.getId())
                    .title(m.getTitle())
                    .heartId(heart.getId())
                    .heartName(heart.getName())
                    .heartUrl(heart.getImageUrl())
                    .emojiId(emojiId)
                    .emojiName(emojiName)
                    .emojiUrl(emojiUrl)
                    .createdDate(m.getCreatedDate())
                    .expiredDate(m.getExpiredDate()).build();
            if (isSelf) {
                messageData.setRead(m.isRead());
            }

            receivedMessageData.getMessageList().add(messageData);
        } else {
            // Expired, need to persist to DB
            m.deleteMessage();
            messageRepository.save(m);
        }
    }

    @Transactional
    public MessageData getMessageDetail(long messageId, String userId) {

        // Check if message belongs to the user
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);

        // Check if userId matches receiver of message
        if (!message.getReceiver().getId().equals(userId)) {
            throw new UnAuthorizedException("본인의 메시지만 상세열람할 수 있습니다.");
        }

        // Read message and persist
        message.readMessage();
        messageRepository.save(message);

        Heart heart = message.getHeart();
        Emoji emoji = message.getEmoji();

        log.info(userId + " 유저가 " + messageId + " 메시지를 상세조회 했습니다.");

        return MessageData.builder()
                .messageId(message.getId())
                .title(message.getTitle())
                .heartId(heart.getId())
                .heartName(heart.getName())
                .heartUrl(heart.getImageUrl())
                .emojiId(emoji != null ? emoji.getId() : -1)
                .emojiName(emoji != null ? emoji.getName() : null)
                .emojiUrl(emoji != null ? emoji.getImageUrl() : null)
                .createdDate(message.getCreatedDate())
                .expiredDate(message.getExpiredDate())
                .isRead(message.isRead())
                .isReported(message.isReported())
                .isStored(message.isStored())
                .content(message.getContent())
                .shortDescription(heart.getShortDescription())
                .build();
    }


}

