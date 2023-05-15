package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverIdAndIsActiveTrue(String userId, Sort sort);

    List<Message> findByReceiverIdAndSenderIp(String receiverId, String senderIp);

    @Query(value = "SELECT COUNT(*) FROM message WHERE sender_ip IS NULL OR sender_ip != :senderIp", nativeQuery = true)
    Long countBySenderIpNotOrIsNull(@Param(value = "senderIp") String senderIp);

}
