package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.UserHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHeartRepository extends JpaRepository<UserHeart, Long> {

    List<UserHeart> findAllByUserId(String userId);

//    @Query(value = "select h.id, h.name, h.image_url, h.short_description, h.long_description, h.type, h.acq_condition, uh.user_id " +
//            "from user_heart as uh left outer join heart as h " +
//            "on h.id = uh.heart_id " +
//            "where uh.user_id = :userId", nativeQuery = true)
//    public List<Heart> findMyHearts(@Param("userId") String userId);
}
