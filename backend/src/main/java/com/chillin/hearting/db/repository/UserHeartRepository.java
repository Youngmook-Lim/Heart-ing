package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.UserHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHeartRepository extends JpaRepository<UserHeart, Long> {
}
