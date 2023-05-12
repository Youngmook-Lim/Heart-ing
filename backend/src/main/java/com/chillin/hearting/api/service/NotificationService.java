package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.NotificationData;
import com.chillin.hearting.api.data.NotificationListData;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.Notification;
import com.chillin.hearting.db.repository.NotificationRepository;
import com.chillin.hearting.exception.NotificationNotFoundException;
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
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationListData getNotifications(String userId) {
        List<Notification> notificationList = notificationRepository.findByUserIdAndIsActiveTrue(userId, Sort.by(Sort.Direction.DESC, "createdDate"));
//        System.out.println(notificationList.get(0));
        NotificationListData notificationListData = NotificationListData.builder().notificationList(new ArrayList<>()).build();

        for (Notification n : notificationList) {
            processNotification(n, notificationListData);
        }

        log.info(userId + " 유저가 알림 리스트를 조회했습니다. 총 " + notificationListData.getNotificationList().size() + "개의 알림이 조회되었습니다.");

        return notificationListData;
    }

    public void processNotification(Notification n, NotificationListData notificationListData) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime expiredDate = n.getExpiredDate();

        Heart heart = n.getHeart();
        Message message = n.getMessage();
        String heartName = heart != null ? heart.getName() : null;
        String heartUrl = heart != null ? heart.getImageUrl() : null;
        Long messageId = message != null ? message.getId() : null;

        if (expiredDate.isAfter(now)) {
            // Not yet expired
            NotificationData notificationData = NotificationData.builder()
                    .notificationId(n.getId())
                    .heartName(heartName)
                    .heartUrl(heartUrl)
                    .messageId(messageId)
                    .type(n.getType())
                    .createdDate(n.getCreatedDate())
                    .isChecked(n.isChecked()).build();
            notificationListData.getNotificationList().add(notificationData);
        } else {
            // Expired, need to persist to DB
            n.deleteNotification();
            notificationRepository.save(n);
        }
    }

    @Transactional
    public Long readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(NotificationNotFoundException::new);
        notification.readNotification();
        notificationRepository.save(notification);

        log.info(notificationId + " 알림을 읽었습니다.");

        return notification.getId();
    }
}
