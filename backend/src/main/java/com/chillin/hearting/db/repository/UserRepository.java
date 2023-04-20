package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

}
