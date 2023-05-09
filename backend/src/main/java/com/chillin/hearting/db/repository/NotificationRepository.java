package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Notification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndIsActiveTrue(String userId, Sort sort);
}
