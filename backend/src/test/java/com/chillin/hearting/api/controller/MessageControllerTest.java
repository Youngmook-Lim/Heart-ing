package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.SendMessageData;
import com.chillin.hearting.api.request.ReportReq;
import com.chillin.hearting.api.request.SendMessageReq;
import com.chillin.hearting.api.service.MessageService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.ControllerExceptionHandler;
import com.chillin.hearting.exception.UserNotFoundException;
import com.chillin.hearting.exception.WrongUserException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    private MockMvc mockMvc;
    private Gson gson;

    private final long messageId = 0L;
    private final long heartId = 0L;
    private final long reportId = 0L;
    private final long emojiId = 0L;
    private final String content = "Test content";

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void failSendMessage_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages";
        User user = User.builder().id("otherSender").build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(SendMessageReq.builder().heartId(0L).senderId("sender").receiverId("receiver").title("title").build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is(WrongUserException.DEFAULT_MESSAGE)));

    }

    @Test
    public void failSendMessage_ServiceError() throws Exception {
        // given
        final String url = "/api/v1/messages/";
        User user = User.builder().id("sender").build();

        SendMessageReq sendMessageReq = SendMessageReq.builder()
                .heartId(heartId)
                .senderId("sender")
                .receiverId("receiver")
                .title("title")
                .build();

        // Mock the behavior of messageService to throw a UserNotFoundException using doThrow().when()
        doThrow(new UserNotFoundException()).when(messageService)
                .sendMessage(sendMessageReq.getHeartId(), sendMessageReq.getSenderId(), sendMessageReq.getReceiverId(), sendMessageReq.getTitle(), sendMessageReq.getContent(), "127.0.0.1");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(sendMessageReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(UserNotFoundException.DEFAULT_MESSAGE)));
    }

    @Test
    public void successSendMessage() throws Exception {
        // given
        final String url = "/api/v1/messages/";
        User user = User.builder().id("sender").build();

        SendMessageReq sendMessageReq = SendMessageReq.builder()
                .heartId(heartId)
                .senderId("sender")
                .receiverId("receiver")
                .title("title")
                .build();

        SendMessageData expectedResponse = SendMessageData.builder()
                .messageId(messageId)
                .heartId(sendMessageReq.getHeartId())
                .build();

        doReturn(expectedResponse).when(messageService).sendMessage(sendMessageReq.getHeartId(), sendMessageReq.getSenderId(), sendMessageReq.getReceiverId(), sendMessageReq.getTitle(), sendMessageReq.getContent(), "127.0.0.1");


        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(sendMessageReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.messageId", is((int) messageId)))
                .andExpect(jsonPath("$.data.heartId", is((int) heartId)))
                .andExpect(jsonPath("$.status", is("success")));
    }

    @Test
    public void successDeleteMessage() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId;
        User user = User.builder().id("sender").build();

        doReturn(false).when(messageService).deleteMessage(messageId, user.getId());


        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("메시지가 성공적으로 삭제되었습니다.")))
                .andExpect(jsonPath("$.status", is("success")));
    }

    @Test
    public void successReportMessage() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/reports";
        User user = User.builder().id("sender").build();

        ReportReq reportReq = ReportReq.builder()
                .content(content)
                .build();

        doReturn(reportId).when(messageService).reportMessage(messageId, user.getId(), reportReq.getContent());


        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(reportReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("메시지가 성공적으로 신고되었습니다.")))
                .andExpect(jsonPath("$.status", is("success")));
    }

    @Test
    public void successAddEmoji() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/emojis/" + emojiId;
        User user = User.builder().id("sender").build();

        doReturn(emojiId).when(messageService).addEmoji(messageId, user.getId(), emojiId);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("이모지가 성공적으로 변경되었습니다.")))
                .andExpect(jsonPath("$.status", is("success")));
    }


}
