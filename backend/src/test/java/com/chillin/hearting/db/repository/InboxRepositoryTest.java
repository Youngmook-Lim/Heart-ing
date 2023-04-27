package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.MessageNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InboxRepositoryTest {

    @Autowired
    private InboxRepository inboxRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private EmojiRepository emojiRepository;
    @Autowired
    private UserRepository userRepository;

    private User user1 = createUser("1", "test1.com", "nick1");
    private User user2 = createUser("2", "test2.com", "nick2");
    private Heart heart = createDefaultHeart();
    private Emoji emoji = createEmoji();

    @Test
    public void 영구메시지저장() {
        // given
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Heart savedHeart = heartRepository.save(heart);
        Emoji savedEmoji = emojiRepository.save(emoji);
        Message message = Message.builder()
                .id(1L)
                .heart(heart)
                .emoji(emoji)
                .sender(user1)
                .receiver(user2)
                .title("title")
                .content("content")
                .build();
        Message savedMessage = messageRepository.save(message);

        assertThat(savedMessage.isStored()).isEqualTo(false);

        // when
        savedMessage.toInbox();
        Message storedMessage = inboxRepository.save(savedMessage);

        // then
        assertThat(storedMessage.isStored()).isEqualTo(true);
    }

    @Test
    public void 영구보관메시지조회() {
        // given
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Heart savedHeart = heartRepository.save(heart);
        Emoji savedEmoji = emojiRepository.save(emoji);
        Message message1 = Message.builder()
                .id(1L)
                .heart(heart)
                .emoji(emoji)
                .sender(user1)
                .receiver(user2)
                .title("title")
                .content("content")
                .build();
        Message savedMessage1 = inboxRepository.save(message1);

        Message message2 = Message.builder()
                .id(2L)
                .heart(heart)
                .emoji(emoji)
                .sender(user1)
                .receiver(user2)
                .title("title")
                .content("content")
                .build();
        Message savedMessage2 = inboxRepository.save(message2);

        // when
        savedMessage1.toInbox();
        savedMessage2.toInbox();
        Message storedMessage1 = inboxRepository.save(savedMessage1);
        Message storedMessage2 = inboxRepository.save(savedMessage2);
        List<Message> inboxList = inboxRepository.findAllByReceiverIdAndIsStored(savedUser2.getId(), true);
        // then

        assertThat(inboxList.size()).isEqualTo(2);
    }

    @Test
    public void 영구보관메시지상세조회() throws Exception {
        // given
        User sender = userRepository.save(user1);
        User receiver = userRepository.save(user2);
        Heart savedHeart = heartRepository.save(heart);
        Emoji savedEmoji = emojiRepository.save(emoji);
        Long testId = 1L;
        Message message = Message.builder()
                .id(testId)
                .heart(heart)
                .emoji(emoji)
                .sender(sender)
                .receiver(receiver)
                .title("title")
                .content("content")
                .build();
        message.toInbox();
        Message savedMessage = inboxRepository.save(message);

        // when
        Message findMessage = inboxRepository.findByIdAndReceiverIdAndIsStored(testId, receiver.getId(), true).orElseThrow(MessageNotFoundException::new);

        // then
        assertThat(findMessage.getId()).isEqualTo(savedMessage.getId());
    }

    @Test
    public void 영구메시지삭제() {
        // given
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Heart savedHeart = heartRepository.save(heart);
        Emoji savedEmoji = emojiRepository.save(emoji);
        Message message = Message.builder()
                .id(1L)
                .heart(heart)
                .emoji(emoji)
                .sender(user1)
                .receiver(user2)
                .title("title")
                .content("content")
                .build();
        message.toInbox();
        Message savedMessage = messageRepository.save(message);

        assertThat(savedMessage.isStored()).isEqualTo(true);

        // when
        savedMessage.deleteInbox();
        System.out.println(savedMessage.isStored());
        inboxRepository.save(savedMessage);
        Message storedMessage = inboxRepository.findById(savedMessage.getId()).orElseThrow(MessageNotFoundException::new);

        // then
        assertThat(storedMessage.isStored()).isEqualTo(false);
    }

    public User createUser(String id, String email, String nickname) {
        if ("".equals(email)) email = "test.com";
        if ("".equals(nickname)) nickname = "test-nick";
        User user = User.builder()
                .id(id)
                .type("ROLE_TEST")
                .email(email)
                .nickname(nickname)
                .build();
        return user;
    }

    public Heart createDefaultHeart() {
        Heart heart = Heart.builder()
                .name("호감 하트")
                .imageUrl("test.com")
                .shortDescription("짧은 설명 !")
                .longDescription("호감의 탄생 스토리")
                .acqCondition("기본 제공")
                .type("DEFAULT")
                .build();

        return heart;
    }

    public Heart createSpecialHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("행성 하트")
                .imageUrl("universe.com")
                .shortDescription("우주에 단 하나 뿐인 너 !")
                .longDescription("우주의 탄생 스토리")
                .acqCondition("특정인에게 5회 이상 메시지 전송")
                .type("SPECIAL")
                .build();

        return heart;
    }

    public Emoji createEmoji() {
        Emoji emoji = Emoji.builder()
                .name("emoji")
                .imageUrl("emojiUrl")
                .build();
        return emoji;
    }

}
