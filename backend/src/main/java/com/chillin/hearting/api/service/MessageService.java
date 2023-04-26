package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.api.data.TotalMessageCountData;
import com.chillin.hearting.db.domain.*;
import com.chillin.hearting.db.repository.*;
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
    private final BlockedUserRepository blockedUserRepository;
    private final ReportRepository reportRepository;
    private final EmojiRepository emojiRepository;

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

    // 홈 화면 - 서비스 전체 누적 메시지 수
    public TotalMessageCountData totalMessageCount() {

        Long count = messageRepository.count();

        log.debug("서비스 누적 메시지 수 {}", count);

        TotalMessageCountData totalMessageCountData = TotalMessageCountData.builder().totalHeartCount(count).build();

        return totalMessageCountData;

    }

}

