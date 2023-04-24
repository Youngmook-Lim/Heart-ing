package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

}