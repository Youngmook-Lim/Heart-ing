package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.request.SendMessageReq;
import com.chillin.hearting.api.response.SendMessageRes;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    private MockMvc mockMvc;
    private Gson gson;

    @Mock
    private MessageService messageService;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void failSendMessage_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages";
        HttpServletRequest req = mock(HttpServletRequest.class);
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
        HttpServletRequest req = mock(HttpServletRequest.class);
        User user = User.builder().id("sender").build();

        SendMessageReq sendMessageReq = SendMessageReq.builder()
                .heartId(0L)
                .senderId("sender")
                .receiverId("receiver")
                .title("title")
                .build();

        // Mock the behavior of messageService to throw a UserNotFoundException using doThrow().when()
        doThrow(new UserNotFoundException()).when(messageService)
                .sendMessage(sendMessageReq.getHeartId(), sendMessageReq.getSenderId(), sendMessageReq.getReceiverId(), sendMessageReq.getTitle(), null, null);

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
        HttpServletRequest req = mock(HttpServletRequest.class);
        User user = User.builder().id("sender").build();

        SendMessageReq sendMessageReq = SendMessageReq.builder()
                .heartId(0L)
                .senderId("sender")
                .receiverId("receiver")
                .title("title")
                .build();

        SendMessageRes expectedResponse = SendMessageRes.builder()
                .data(Map.of(
                        "messageId", 1L,
                        "heartId", 0L,
                        "heartName", "exampleHeartName",
                        "heartUrl", "exampleHeartUrl",
                        "isRead", false
                ))
                .build();

        doReturn(expectedResponse).when(messageService).sendMessage(sendMessageReq.getHeartId(), sendMessageReq.getSenderId(), sendMessageReq.getReceiverId(), sendMessageReq.getTitle(), null, null);

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
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.messageId", is(1)))
                .andExpect(jsonPath("$.data.heartId", is(0)))
                .andExpect(jsonPath("$.data.heartName", is("exampleHeartName")))
                .andExpect(jsonPath("$.data.heartUrl", is("exampleHeartUrl")))
                .andExpect(jsonPath("$.data.isRead", is(false)));
    }


}
