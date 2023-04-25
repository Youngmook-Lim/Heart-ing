package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboxRepository extends JpaRepository<Message, Long> {

    public List<Message> findAllByReceiverAndIsStored(User user, Boolean isStored);

    public Optional<Message> findByIdAndReceiverAndIsStored(Long id, User user, Boolean isStored);
}
