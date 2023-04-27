package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    public List<Heart> findAllByType(String default_type);

}

