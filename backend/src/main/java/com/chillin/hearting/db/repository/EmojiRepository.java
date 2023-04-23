package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
}
