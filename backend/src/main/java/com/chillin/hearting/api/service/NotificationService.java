package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.NotificationData;
import com.chillin.hearting.api.data.NotificationListData;
import com.chillin.hearting.db.domain.Notification;
import com.chillin.hearting.db.repository.NotificationRepository;
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
        List<Notification> notificationDataList = notificationRepository.findByUserIdAndIsActiveTrue(userId, Sort.by(Sort.Direction.DESC, "createdDate"));

        NotificationListData notificationListData = NotificationListData.builder().notificationList(new ArrayList<>()).build();

        for (Notification n : notificationDataList) {
            processNotification(n, notificationListData);
        }

        return notificationListData;
    }

    public void processNotification(Notification n, NotificationListData notificationListData) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime expiredDate = n.getExpiredDate();
        if (expiredDate.isAfter(now)) {
            // Not yet expired
            NotificationData notificationData = NotificationData.builder()
                    .notificationId(n.getId())
                    .userId(n.getUser().getId())
                    .content(n.getContent())
                    .type(n.getType())
                    .createdDate(n.getCreatedDate())
                    .expiredDate(n.getExpiredDate())
                    .isChecked(n.isChecked())
                    .isActive(n.isActive()).build();
            notificationListData.getNotificationList().add(notificationData);
        } else {
            // Expired, need to persist to DB
            n.deleteNotification();
            notificationRepository.save(n);
        }
    }
}
