package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private EmojiRepository emojiRepository;

    private final Heart heart = Heart.builder().name("testHeart").imageUrl("testUrl").shortDescription("short").longDescription("long").type("type").acqCondition("acq").build();
    private final User sender = User.builder().id("1").type("KAKAO").email("email1").nickname("test1").build();
    private final User receiver = User.builder().id("2").type("KAKAO").email("email2").nickname("test2").build();
    private final Emoji emoji = Emoji.builder().name("emoji").imageUrl("emojiUrl").build();

    @Test
    void messageRepositoryIsNotNull() {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    void successSendMessage() {
        // given
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
    void successUpdateUserMessageTotal() {
        // given
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

    @Test
    void successDeleteMessage() {
        // given
        Message message = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();

        // when
        heartRepository.save(heart);
        userRepository.save(sender);
        userRepository.save(receiver);
        emojiRepository.save(emoji);

        Message messagePrev = messageRepository.save(message);
        // then
        assertThat(messagePrev.isActive()).isTrue();

        // when
        messagePrev.deleteMessage();
        Message messageAfter = messageRepository.save(messagePrev);

        // then
        assertThat(messageAfter.getId()).isNotNull();
        assertThat(messageAfter.isActive()).isFalse();
    }

    @Test
    void successReportMessage() {
        // given
        Message message = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();

        // when
        heartRepository.save(heart);
        userRepository.save(sender);
        userRepository.save(receiver);
        emojiRepository.save(emoji);
        Message savedMessage = messageRepository.save(message);

        savedMessage.reportMessage();
        Message updatedMessage = messageRepository.save(savedMessage);

        // then
        assertThat(updatedMessage.isReported()).isTrue();
    }

    @Test
    void successGetMessageDetail() {
        // given
        Message message1 = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();
        Message message2 = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();
        Message message3 = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();
        Message message4 = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();
        Message message5 = Message.builder().heart(heart).emoji(emoji).sender(sender).receiver(receiver).title("testTitle").content("message content").senderIp("127.0.0.1").build();

        // when
        heartRepository.save(heart);
        userRepository.save(sender);
        userRepository.save(receiver);
        emojiRepository.save(emoji);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);

        List<Message> messageList = messageRepository.findAll();

        // then
        assertThat(messageList.size()).isEqualTo(5);

    }
}
