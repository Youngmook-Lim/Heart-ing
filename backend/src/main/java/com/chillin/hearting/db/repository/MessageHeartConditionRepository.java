package com.chillin.hearting.db.repository;


import com.chillin.hearting.db.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageHeartConditionRepository extends JpaRepository<Message, Long> {

    @Query(value = "select count(*) from message " +
            "where sender_id= :userId and heart_id= :heartId " +
            "group by receiver_id " +
            "order by count(*) desc " +
            "limit 1", nativeQuery = true)
    public Integer findMaxMessageCountToSameUser(@Param(value = "userId") String userId, @Param(value = "heartId") Long heartId);
}
