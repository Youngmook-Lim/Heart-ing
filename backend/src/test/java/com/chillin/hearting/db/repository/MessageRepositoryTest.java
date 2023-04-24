package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotFoundException;
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
    public void messageRepositoryIsNotNull() {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    public void successSendMessage() {
        // given
        Heart heart = Heart.builder().name("testHeart").imageUrl("testUrl").shortDescription("short").longDescription("long").type("type").acqCondition("acq").build();
        User sender = User.builder().id("1").type("KAKAO").email("email1").nickname("test1").build();
        User receiver = User.builder().id("2").type("KAKAO").email("email2").nickname("test2").build();
//        Heart heart = new Heart(null, "testHeart", "testHeartUrl", "short", "long", "type", "acq");
//        User sender = new User("1", "KAKAO", "test1@test.com", "test1", "refresh1", null, null, "status", 0, 'A', "role", 0L);
//        User receiver = new User("2", "KAKAO", "test2@test.com", "test2", "refresh2", null, null, "status", 0, 'A', "role", 0L);
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

    @Test
    public void successUpdateUserMessageTotal() {
        // given
        Heart heart = Heart.builder().name("testHeart").imageUrl("testUrl").shortDescription("short").longDescription("long").type("type").acqCondition("acq").build();
        User sender = User.builder().id("1").type("KAKAO").email("email1").nickname("test1").build();
        User receiver = User.builder().id("2").type("KAKAO").email("email2").nickname("test2").build();
        Emoji emoji = Emoji.builder().name("emoji").imageUrl("emojiUrl").build();
        Message message = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();


        // when
        heartRepository.save(heart);
        userRepository.save(sender);
        userRepository.save(receiver);
        emojiRepository.save(emoji);

        Message result = messageRepository.save(message);
        User beforeUser = userRepository.findById(result.getReceiver().getId()).orElseThrow(NotFoundException::new);
        long before = beforeUser.getMessageTotal();

        beforeUser.updateMessageTotal();

        userRepository.save(beforeUser);
        User afterUser = userRepository.findById(result.getReceiver().getId()).orElseThrow(NotFoundException::new);
        long after = afterUser.getMessageTotal();

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(after - before).isEqualTo(1);
    }
}
