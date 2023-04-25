package com.chillin.hearting.api.service;

import com.chillin.hearting.db.domain.Emoji;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.InboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InboxServiceTest {

    @InjectMocks
    private InboxService inboxService;

    @Mock
    private InboxRepository inboxRepository;

    private User user1 = createUser("1", "test1.com", "nick1");
    private User user2 = createUser("2", "test2.com", "nick2");
    private Heart heart = createDefaultHeart();
    private Emoji emoji = createEmoji();
    private Message savedMessage = createMessage();

    @Test
    public void 메시지영구보관() {
        // given

        // mocking
        when(inboxRepository.findById(any())).thenReturn(Optional.ofNullable(savedMessage));

        // when
        assertThat(savedMessage.isStored()).isEqualTo(false);
        inboxService.storeMessage(savedMessage.getId());

        // then
        Optional<Message> findMessage = inboxRepository.findById(savedMessage.getId());
        assertThat(findMessage.get().isStored()).isEqualTo(true);
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

    public Emoji createEmoji() {
        Emoji emoji = Emoji.builder()
                .name("emoji")
                .imageUrl("emojiUrl")
                .build();

        return emoji;
    }

    public Message createMessage() {
        return Message.builder()
                .id(1L)
                .heart(heart)
                .emoji(emoji)
                .sender(user1)
                .receiver(user2)
                .title("title")
                .content("content")
                .build();
    }

}
