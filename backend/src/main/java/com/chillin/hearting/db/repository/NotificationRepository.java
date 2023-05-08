package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
