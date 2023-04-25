package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {

    Optional<BlockedUser> findByUserId(String userId);
}
