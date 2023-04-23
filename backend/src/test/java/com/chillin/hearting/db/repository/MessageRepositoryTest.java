package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private EmojiRepository emojiRepository;

    @Test
    public void MessageRepositoryIsNotNull() {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    public void SendMessage() {
        // given
        Heart heart = new Heart(null, "testHeart", "testHeartUrl", "short", "long", "type", "acq");
        User sender = new User("1", "KAKAO", "test1@test.com", "test1", "refresh1", null, null, "status", 0, 'A', "role", 0L);
        User receiver = new User("2", "KAKAO", "test2@test.com", "test2", "refresh2", null, null, "status", 0, 'A', "role", 0L);
        Emoji emoji = Emoji.builder().name("emoji").imageUrl("emojiUrl").build();
        Message message = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();


        // when
        heartRepository.save(heart);
        userRepository.save(sender);
        userRepository.save(receiver);
        emojiRepository.save(emoji);

        Message result = messageRepository.save(message);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getSender().getId()).isEqualTo(sender.getId());
        assertThat(result.getReceiver().getId()).isEqualTo(receiver.getId());
        assertThat(result.getHeart().getId()).isEqualTo(heart.getId());
        assertThat(result.getEmoji().getId()).isEqualTo(emoji.getId());
    }
}
