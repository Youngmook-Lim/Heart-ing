package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.MessageRepository;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.HeartNotFoundException;
import com.chillin.hearting.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HeartRepository heartRepository;

    @Mock
    private MessageRepository messageRepository;

    private final long heartId = 0L;
    private final String senderId = "senderId";
    private final String receiverId = "receiverId";
    private final String title = "title";
    private final String content = "content";
    private final String senderIp = "senderIp";
    private final User receiver = User.builder().id("receiver").build();
    private final User sender = User.builder().id("sender").build();
    private final Heart heart = Heart.builder().id(0L).name("testHeart").build();

    @Test
    public void failSendMessage_NoReceiver() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(receiverId);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(exception.getMessage(), "해당 유저를 찾을 수 없습니다.");
    }

    @Test
    public void failSendMessage_NoSender() {
        //given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(Optional.empty()).when(userRepository).findById(senderId);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(exception.getMessage(), "해당 유저를 찾을 수 없습니다.");
    }

    @Test
    public void failSendMessage_NoHeart() {
        //given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.empty()).when(heartRepository).findById(heartId);

        // when
        HeartNotFoundException exception = assertThrows(HeartNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(exception.getMessage(), "해당 하트를 찾을 수 없습니다.");
    }

    @Test
    public void successSendMessage() {
        // given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(heart)).when(heartRepository).findById(heartId);
        doReturn(Message.builder().id(0L).heart(heart).receiver(receiver).sender(sender).title(title).content(content).senderIp(senderIp).build()).when(messageRepository).save(any(Message.class));

        // when
        final MessageData message = messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp);

        // then
        assertThat(message.getHeartName()).isEqualTo("testHeart");

        // verify
        verify(userRepository, times(1)).findById(receiverId);
        verify(userRepository, times(1)).findById(senderId);
        verify(heartRepository, times(1)).findById(heartId);
        verify(messageRepository, times(1)).save(any(Message.class));

    }
}
