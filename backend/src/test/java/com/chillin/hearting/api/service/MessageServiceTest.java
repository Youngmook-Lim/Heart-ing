package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.EmojiData;
import com.chillin.hearting.api.data.ReportData;
import com.chillin.hearting.api.data.SendMessageData;
import com.chillin.hearting.db.domain.*;
import com.chillin.hearting.db.repository.*;
import com.chillin.hearting.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HeartRepository heartRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EmojiRepository emojiRepository;

    private final long heartId = 0L;
    private final String senderId = "senderId";
    private final String receiverId = "receiverId";
    private final String title = "title";
    private final String content = "content";
    private final String senderIp = "senderIp";
    private final String emojiUrl = "emojiUrl";
    private final long messageId = 0L;
    private final long emojiId = 0L;

    private final User receiver = User.builder().id(receiverId).messageTotal(0L).build();
    private final User sender = User.builder().id(senderId).build();
    private final Heart heart = Heart.builder().id(0L).name("testHeart").build();
    private final Message message = Message.builder().id(0L).sender(sender).receiver(receiver).build();
    private final Emoji emoji = Emoji.builder().id(0L).imageUrl(emojiUrl).build();

    @BeforeEach
    public void setupIsActive() {
        message.undeleteMessage();
    }

    // sendMessage
    @Test
    void failSendMessage_NoReceiver() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(receiverId);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(UserNotFoundException.DEFAULT_MESSAGE, exception.getMessage());
    }

    @Test
    void failSendMessage_NoSender() {
        //given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(Optional.empty()).when(userRepository).findById(senderId);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(UserNotFoundException.DEFAULT_MESSAGE, exception.getMessage());
    }

    @Test
    void failSendMessage_NoHeart() {
        //given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.empty()).when(heartRepository).findById(heartId);

        // when
        HeartNotFoundException exception = assertThrows(HeartNotFoundException.class, () -> messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp));

        // then
        assertEquals(HeartNotFoundException.DEFAULT_MESSAGE, exception.getMessage());
    }

    @Test
    void successSendMessage() {
        // given
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(receiver).when(userRepository).save(receiver);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(heart)).when(heartRepository).findById(heartId);
        doReturn(Message.builder().id(0L).heart(heart).receiver(receiver).sender(sender).title(title).content(content).senderIp(senderIp).build()).when(messageRepository).save(any(Message.class));

        // when
        final SendMessageData message = messageService.sendMessage(heartId, senderId, receiverId, title, content, senderIp);

        // then
        assertThat(message.getHeartName()).isEqualTo("testHeart");

        // verify
        verify(userRepository, times(1)).findById(receiverId);
        verify(userRepository, times(1)).findById(senderId);
        verify(heartRepository, times(1)).findById(heartId);
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    // deleteMessage
    @Test
    void failDeleteMessage_NoMessage() {
        //given
        doReturn(Optional.empty()).when(messageRepository).findById(messageId);

        // when
        MessageNotFoundException exception = assertThrows(MessageNotFoundException.class, () -> messageService.deleteMessage(messageId, receiverId));

        // then
        assertEquals(MessageNotFoundException.DEFAULT_MESSAGE, exception.getMessage());
    }

    @Test
    void successDeleteMessage() {
        //given
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Message.builder().id(0L).receiver(receiver).build()).when(messageRepository).save(any(Message.class));

        // when
        boolean result = messageService.deleteMessage(messageId, receiverId);

        // then
        assertFalse(result);
    }

    @Test
    void successReportMessage() {
        //given
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(message).when(messageRepository).save(any(Message.class));
        doReturn(sender).when(userRepository).save(any(User.class));
        doReturn(Report.builder().id(1L).build()).when(reportRepository).save(any(Report.class));

        // when
        ReportData data = messageService.reportMessage(messageId, receiverId, content);

        // then
        assertNotNull(data);
        assertTrue(data.isLoggedInUser());

        // verify
        verify(messageRepository, times(1)).findById(messageId);
        verify(userRepository, times(1)).findById(senderId);
        verify(userRepository, times(1)).findById(receiverId);
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void failReportMessage_NoAuthorization() {
        //given
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        String differentId = "differentId";

        // when
        UnAuthorizedException exception = assertThrows(UnAuthorizedException.class, () -> messageService.reportMessage(messageId, differentId, content));

        // then
        assertEquals(exception.getMessage(), "본인이 받은 메시지만 신고할 수 있습니다.");
    }

    @Test
    void failReportMessage_AlreadyReported() {
        //given
        message.reportMessage();
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);

        // when
        MessageAlreadyReportedException exception = assertThrows(MessageAlreadyReportedException.class, () -> messageService.reportMessage(messageId, receiverId, content));

        // then
        assertEquals(exception.getMessage(), MessageAlreadyReportedException.DEFAULT_MESSAGE);
    }

    @Test
    void successAddEmoji() {
        //given
        String emojiUrl = "emojiUrl";
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Optional.of(emoji)).when(emojiRepository).findById(emojiId);
        message.updateEmoji(emoji);
        doReturn(message).when(messageRepository).save(any(Message.class));

        // when
        EmojiData returned = messageService.addEmoji(messageId, receiverId, MessageServiceTest.this.emojiId);

        // then
        assertNotNull(returned);
        assertEquals(MessageServiceTest.this.emojiUrl, returned.getEmojiUrl());

        // verify
        verify(messageRepository, times(1)).findById(messageId);
        verify(emojiRepository, times(1)).findById(MessageServiceTest.this.emojiId);
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void failAddEmoji_NoAuthorization() {
        //given
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);
        doReturn(Optional.of(emoji)).when(emojiRepository).findById(emojiId);
        String differentId = "differentId";

        // when
        UnAuthorizedException exception = assertThrows(UnAuthorizedException.class, () -> messageService.addEmoji(messageId, differentId, emojiId));

        // then
        assertEquals(exception.getMessage(), "본인이 받은 메시지에만 이모지를 달 수 있습니다.");
    }

}
