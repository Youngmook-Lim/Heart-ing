package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.UserHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHeartRepository extends JpaRepository<UserHeart, Long> {

    List<UserHeart> findAllByUserId(String userId);

    List<UserHeart> findAllByUserIdOrderByHeartId(String userId);

    List<UserHeart> findByHeartIdAndUserId(Long heartId, String userId);
}
