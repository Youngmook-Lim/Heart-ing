package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SentMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndExpiredDateAfterOrderByExpiredDate(String userId, LocalDateTime now);

    Optional<Message> findByIdAndSenderId(Long messageId, String userId);
}
