package com.chillin.hearting.api.service;

import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private UserRepository userRepository;

    long heartId = 0L;
    String senderId = "senderId";
    String receiverId = "receiverId";
    String content = "content";

    @Test
    public void failSendMessage_NoReceiver() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(receiverId);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, content));

        // then
        assertEquals(exception.getMessage(), "해당 유저를 찾을 수 없습니다.");
    }
}
