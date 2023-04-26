package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboxRepository extends JpaRepository<Message, Long> {

    public Optional<Message> findByIdAndIsActive(Long messageId, Boolean isActive);

    public List<Message> findAllByReceiverAndIsStoredAndIsActive(User user, Boolean isStored, Boolean isActive);

    public Optional<Message> findByIdAndReceiverAndIsStoredAndIsActive(Long id, User user, Boolean isStored, Boolean isActive);

}
