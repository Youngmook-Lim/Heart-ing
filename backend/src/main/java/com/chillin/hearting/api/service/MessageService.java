package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.api.data.ReceivedMessageData;
import com.chillin.hearting.api.data.SendMessageData;
import com.chillin.hearting.db.domain.*;
import com.chillin.hearting.db.repository.*;
import com.chillin.hearting.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final ReportRepository reportRepository;
    private final EmojiRepository emojiRepository;

    @Transactional
    public SendMessageData sendMessage(long heartId, String senderId, String receiverId, String title, String content, String senderIp) {

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

        return SendMessageData.builder()
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
            throw new UnAuthorizedException("본인에게 온 메시지만 삭제할 수 있습니다.");
        }

        // Check if already deleted
        if (!message.isActive()) {
            throw new MessageAlreadyDeletedException();
        }

        message.deleteMessage();

        message = messageRepository.save(message);

        return message.isActive();
    }

    @Transactional
    public Long reportMessage(long messageId, String userId, String content) {

        // Check if message, reportedUser, reporter exist
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        User reportedUser = userRepository.findById(message.getSender().getId()).orElseThrow(UserNotFoundException::new);
        User reporter = userRepository.findById(message.getReceiver().getId()).orElseThrow(UserNotFoundException::new);

        // Check if userId matches the receiverId
        if (!message.getReceiver().getId().equals(userId)) {
            throw new UnAuthorizedException("본인이 받은 메시지만 신고할 수 있습니다.");
        }

        // Check if already reported
        if (message.isReported()) {
            throw new MessageAlreadyReportedException();
        }

        // Update message
        message.reportMessage();


        // Update user and add to BlockedUser if necessary

        reportedUser.reportUser();

        int reportedCnt = reportedUser.getReportedCount();

        if (reportedCnt == 3 || reportedCnt == 5) {
            char newStatus = reportedCnt == 3 ? 'P' : 'O';

            reportedUser.updateUserStatus(newStatus);

            // Add to Blocked User
            BlockedUser blockedUser = BlockedUser.builder().user(reportedUser).build();
            blockedUser.prePersist();
            blockedUser.updateEndDate(newStatus);
            // Persist blockedUser
            blockedUserRepository.save(blockedUser);
        }

        // Persist message
        message = messageRepository.save(message);
        // Persist reportedUser
        reportedUser = userRepository.save(reportedUser);

        // Create Report
        Report report = Report.builder().message(message).reporter(reporter).reportedUser(reportedUser).content(content).build();
        report = reportRepository.save(report);

        return report.getId();
    }

    @Transactional
    public Long addEmoji(long messageId, String userId, long emojiId) {

        // Check if message, emoji exist
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        Emoji emoji = emojiId == -1 ? null : emojiRepository.findById(emojiId).orElseThrow(EmojiNotFoundException::new);

        // Check if userId matches receiver of message
        if (!message.getReceiver().getId().equals(userId)) {
            throw new UnAuthorizedException("본인이 받은 메시지에만 이모지를 달 수 있습니다.");
        }

        message.updateEmoji(emoji);

        message = messageRepository.save(message);

        return message.getId();
    }

    @Transactional
    public ReceivedMessageData getReceivedMessages(String userId, boolean isSelf) {

        // Get list of all received messages
        List<Message> initialList = messageRepository.findByReceiverIdAndIsActiveTrue(userId);
        log.debug(initialList.toString());

        // Update expired messages
        ReceivedMessageData receivedMessageData = ReceivedMessageData.builder()
                .messageList(new ArrayList<>()).build();

        for (Message m : initialList) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredDate = m.getExpiredDate();
            if (expiredDate.isAfter(now)) {
                // Not yet expired
                // Create MessageData
                Heart heart = m.getHeart();
                Emoji emoji = m.getEmoji();
                MessageData messageData = MessageData.builder()
                        .messageId(m.getId())
                        .title(m.getTitle())
                        .heartId(heart.getId())
                        .heartName(heart.getName())
                        .heartUrl(heart.getImageUrl())
                        .emojiId(emoji != null ? emoji.getId() : -1)
                        .emojiName(emoji != null ? emoji.getName() : null)
                        .emojiUrl(emoji != null ? emoji.getImageUrl() : null)
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

        return receivedMessageData;
    }

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

