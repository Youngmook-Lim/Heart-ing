package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboxRepository extends JpaRepository<Message, Long> {

    public Optional<Message> findByIdAndIsStored(Long messageId, Boolean isStored);

    public List<Message> findAllByReceiverIdAndIsStored(String userId, Boolean isStored);

    public Optional<Message> findByIdAndReceiverIdAndIsStored(Long messageId, String userId, Boolean isStored);

}
