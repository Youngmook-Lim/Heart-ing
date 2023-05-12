package com.chillin.hearting.db.repository;

import com.chillin.hearting.api.data.HeartConditionDTO;
import com.chillin.hearting.api.data.HeartCountDTO;
import com.chillin.hearting.db.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    public List<Heart> findAllByType(String default_type);

    @Query(value = "select id as heartId, name, image_url as heartUrl, ifnull((select count(*) from message where sender_id=:userId and heart_id=h.id group by heart_id),0) as currentValue " +
            "from heart as h " +
            "where type = 'DEFAULT'", nativeQuery = true)
    public List<HeartConditionDTO> findDefaultHeartSentCount(@Param(value = "userId") String userId);

    @Query(value = "select id as heartId, name, image_url as heartUrl, ifnull((select count(*) from message where sender_id=:userId and heart_id=h.id group by heart_id),0) as currentValue " +
            "from heart as h ", nativeQuery = true)
    public List<HeartCountDTO> findAllHeartSentCount(@Param(value = "userId") String userId);

    @Query(value = "select id as heartId, name, image_url as heartUrl, ifnull((select count(*) from message where receiver_id=:userId and heart_id=h.id group by heart_id),0) as currentValue " +
            "from heart as h ", nativeQuery = true)
    List<HeartCountDTO> findAllHeartReceivedCount(@Param(value = "userId") String userId);
}

