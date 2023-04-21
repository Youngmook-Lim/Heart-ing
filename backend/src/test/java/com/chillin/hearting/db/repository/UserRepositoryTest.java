package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryTest extends JpaRepository<User, String> {


}
