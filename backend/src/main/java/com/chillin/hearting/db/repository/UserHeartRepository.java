package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.domain.UserHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHeartRepository extends JpaRepository<UserHeart, Long> {

    public List<UserHeart> findByUser(User user);
}
