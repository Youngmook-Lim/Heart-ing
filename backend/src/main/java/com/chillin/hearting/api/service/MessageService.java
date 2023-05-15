package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.EmojiData;
import com.chillin.hearting.api.data.ReportData;
import com.chillin.hearting.api.data.SendMessageData;
import com.chillin.hearting.db.domain.*;
import com.chillin.hearting.db.repository.*;
import com.chillin.hearting.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    private final NotificationRepository notificationRepository;

    private static class Sample {
        String title, content;

        public Sample(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    private static final int SEND_TO_ADMIN_INTERVAL_HOURS = 24;
    private static final int SEND_TO_ADMIN_MESSAGE_CNT = 5;
    private static final Long[] SEND_TO_ADMIN_HEART_ID_LIST = {1L, 2L, 3L, 4L, 5L};
    private static final Sample[] SEND_TO_ADMIN_MESSAGE_LIST = {
            new Sample("하팅!은요~", "익명의 메세지 전달 서비스입니다. 하트에 전달고싶은 감정, 마음을 담아 상대방에게 전달해보세요!"),
            new Sample("건의사항", "하팅!을 이용하시면서 건의하시고 싶은 것이나 불편한 사항이 있다면 아래의 링크나 하팅 공식 sns로 건의사항을 보내주세요. 하팅!의 개발진들은 여러분의 불편함을 개선하고자 항상 노력하겠습니다:)"),
            new Sample("하트전달", "상대방에게 전달하고싶은 감정하트를 선택한 후, 전달하고 싶은 메세지가 있다면 함께 보내보세요. 전달한 하트는 24시간만 유지됩니다!"),
            new Sample("보낸하트", "내가 전달한 하트들을 확인해보세요! 내가 전달한 하트에 대한 반응을 알 수 있습니다. 그러나 보낸 메시지들도 24시간 뒤에 사라진다는 것을 주의하세요!"),
            new Sample("받은하트", "내가 받은 하트들을 볼 수 있습니다. 익명의 상대가 보낸 하트들을 눌러 하트의 메시지를 확인해보세요~! 이모티콘으로 메시지에 대한 반응도 남겨 줄 수 있습니다."),
            new Sample("저장소", "받은 하트에서 24시간 뒤에도 사라지지 않았으면 하는 하트가 있다면 저장해보세요! 저장소의 하트는 영원히 간직 할 수 있습니다."),
            new Sample("공유하기", "간단하고 다양한 방법으로 친구들에게 나의 하트판을 공유할 수 있습니다. 카카오톡 공유하기, url을 공유하여 많은 사람들과 하트를 주고받아 보세요!"),
            new Sample("스페셜하트", "하팅!에는 감정하트 외에도 스페셜 하트들이 있습니다. 획득한 스페셜 하트들은 전달 할 수 있습니다. 다양한 스페셜 하트를 모아보세요~!")
    };
    private static final String ADMIN_ID = "3yqolax1ee";
    private static final String NOTIFICATION_MESSAGE_SEND_MESSAGE = "새로운 하트를 받았습니다! 나의 수신함에서 확인 해보세요!";
    private static final String NOTIFICATION_MESSAGE_EMOJI = "보낸 하트에 대한 반응이 달렸습니다!";

    @Scheduled(fixedRate = SEND_TO_ADMIN_INTERVAL_HOURS * 60 * 60000)
    @Transactional
    public void sendScheduledMessageToAdmin() {
        User receiver = userRepository.findById(ADMIN_ID).orElseThrow(UserNotFoundException::new);

        List<Message> messageList = messageRepository.findByReceiverIdAndSenderIp(ADMIN_ID, "ADMIN");
        for (Message m : messageList) {
            m.deleteMessage();
            messageRepository.save(m);
        }

        sendScheduledMessages(receiver);

        log.info("스케줄러가 " + SEND_TO_ADMIN_INTERVAL_HOURS + "시간 주기로 ADMIN 계정에 샘플 메시지 " + SEND_TO_ADMIN_MESSAGE_CNT + "개를 발송했습니다.");
    }

    public void sendScheduledMessages(User user) {
        List<Integer> heartList = new ArrayList<>();
        List<Integer> sampleList = new ArrayList<>();
        for (int i = 0; i < SEND_TO_ADMIN_HEART_ID_LIST.length; i++) {
            heartList.add(i);
        }
        for (int i = 0; i < SEND_TO_ADMIN_MESSAGE_LIST.length; i++) {
            sampleList.add(i);
        }
        Collections.shuffle(heartList);
        Collections.shuffle(sampleList);

        for (int i = 0; i < SEND_TO_ADMIN_MESSAGE_CNT; i++) {

            Long heartId = SEND_TO_ADMIN_HEART_ID_LIST[heartList.get(i)];
            Sample sample = SEND_TO_ADMIN_MESSAGE_LIST[sampleList.get(i)];

            Heart heart = heartRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);

            Message message = Message.builder().heart(heart).receiver(user).sender(null).title(sample.title).content(sample.content).senderIp("ADMIN").build();
            messageRepository.save(message);

            user.updateMessageTotal();
        }
        userRepository.save(user);
    }

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

        // Add notification
        // 알림 종류(R: 받은 하트 E:  보낸 하트 H: 도감)
        Notification notification = Notification.builder()
                .user(receiver)
                .content(NOTIFICATION_MESSAGE_SEND_MESSAGE)
                .type("R")
                .heart(heart)
                .message(message)
                .build();

        notificationRepository.save(notification);

        log.info(senderId + " 유저가 " + receiverId + " 유저에게 " + message.getId() + " 메시지를 발송했습니다.");

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

        log.info(messageId + " 메시지가 삭제되었습니다.");

        return message.isActive();
    }

    @Transactional
    public ReportData reportMessage(long messageId, String userId, String content) {

        // Check if message, reportedUser, reporter exist
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        User reporter = userRepository.findById(message.getReceiver().getId()).orElseThrow(UserNotFoundException::new);
        User reportedUser;
        try {
            reportedUser = userRepository.findById(message.getSender().getId()).orElseThrow(UserNotFoundException::new);
        } catch (NullPointerException e) {
            reportedUser = null;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }

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

        // If reportedUser is not a logged in user
        if (reportedUser == null) {
            return ReportData.builder().isLoggedInUser(false).build();
        }

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

        log.info(messageId + " 메시지가 신고되었습니다. 신고내역의 ID는 " + report.getId() + " 입니다.");

        return ReportData.builder().isLoggedInUser(true).build();
    }

    @Transactional
    public EmojiData addEmoji(long messageId, String userId, long emojiId) {

        // Check if message, emoji exist
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        Emoji emoji = emojiId == -1 ? null : emojiRepository.findById(emojiId).orElseThrow(EmojiNotFoundException::new);

        // Check if userId matches receiver of message
        if (!message.getReceiver().getId().equals(userId)) {
            throw new UnAuthorizedException("본인이 받은 메시지에만 이모지를 달 수 있습니다.");
        }

        message.updateEmoji(emoji);

        message = messageRepository.save(message);

        // 로그인 한 유저가 보낸 메시지에 반응하는 경우
        if (message.getSender() != null) {

            // Add notification
            // 알림 종류(R: 받은 하트 E:  보낸 하트 H: 도감)
            Notification notification = Notification.builder()
                    .user(message.getSender())
                    .content(NOTIFICATION_MESSAGE_EMOJI)
                    .type("E")
                    .heart(message.getHeart())
                    .message(message)
                    .build();

            notificationRepository.save(notification);

        }


//        return message.getEmoji().getId();

        if (emoji == null) {
            log.info(messageId + " 메시지에 반응이 해제되었습니다.");
        } else {
            log.info(messageId + " 메시지에 " + emoji.getName() + " 반응이 추가되었습니다.");
        }

        return EmojiData.builder().emojiUrl(message.getEmoji().getImageUrl()).senderId((message.getSender() != null) ? message.getSender().getId() : null).build();
    }


}

