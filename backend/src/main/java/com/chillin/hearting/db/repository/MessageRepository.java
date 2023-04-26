package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverIdAndIsActiveTrue(String userId);
}
