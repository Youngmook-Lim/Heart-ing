package com.chillin.hearting.api.controller;


import com.chillin.hearting.api.data.MessageData;
import com.chillin.hearting.api.data.ReceivedMessageData;
import com.chillin.hearting.api.service.MessageReceivedService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.ControllerExceptionHandler;
import com.chillin.hearting.exception.MessageDetailFailException;
import com.chillin.hearting.exception.ReceivedMessagesListFailException;
import com.chillin.hearting.exception.UnAuthorizedException;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class MessageReceivedControllerTest {

    @InjectMocks
    private MessageReceivedController messageReceivedController;
    @Mock
    private MessageReceivedService messageReceivedService;
    private MockMvc mockMvc;
    private static final String SUCCESS = "success";
    private final long messageId = 0L;
    private final String content = "Test content";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageReceivedController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void failGetReceivedMessages_ServerFail() throws Exception {
        // given
        final String url = "/api/v1/messages/received/" + "receiver";
        User user = User.builder().id("receiver").build();
        doReturn(null).when(messageReceivedService).getReceivedMessages("receiver", true);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message", is(ReceivedMessagesListFailException.DEFAULT_MESSAGE)));
    }

    @Test
    public void successGetReceivedMessages() throws Exception {
        // given
        final String url = "/api/v1/messages/received/" + "receiver";
        User user = User.builder().id("receiver").build();

        ReceivedMessageData expectedResponse = ReceivedMessageData.builder()
                .messageList(new ArrayList<>())
                .build();

        doReturn(expectedResponse).when(messageReceivedService).getReceivedMessages("receiver", true);


        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.message", is("받은메시지 리스트가 성공적으로 반환되었습니다.")))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @Test
    public void failGetMessageDetail_ServerFail() throws Exception {
        // given
        final String url = "/api/v1/messages/received/detail/" + messageId;
        User user = User.builder().id("receiver").build();
        doReturn(null).when(messageReceivedService).getMessageDetail(messageId, "receiver");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message", is(MessageDetailFailException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failMessageDetail_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages/received/detail/" + messageId;

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is(UnAuthorizedException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failMessageDetail_UnauthorizedUser() throws Exception {
        // given
        final String url = "/api/v1/messages/received/detail/" + messageId;
        User user = User.builder().id("sender").build();
        doThrow(new UnAuthorizedException("본인의 메시지만 상세열람할 수 있습니다."))
                .when(messageReceivedService).getMessageDetail(messageId, "sender");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is("본인의 메시지만 상세열람할 수 있습니다.")));
    }

    @Test
    public void successGetMessageDetail() throws Exception {
        // given
        final String url = "/api/v1/messages/received/detail/" + messageId;
        User user = User.builder().id("receiver").build();

        long emojiId = 0L;
        long heartId = 0L;

        MessageData expectedResponse = MessageData.builder()
                .messageId(messageId)
                .heartId(heartId)
                .emojiId(emojiId)
                .build();

        doReturn(expectedResponse).when(messageReceivedService).getMessageDetail(messageId, "receiver");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setAttribute("user", user);
                            return request;
                        })
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.messageId", is((int) messageId)))
                .andExpect(jsonPath("$.data.heartId", is((int) heartId)))
                .andExpect(jsonPath("$.data.emojiId", is((int) emojiId)))
                .andExpect(jsonPath("$.message", is("메시지 상세가 성공적으로 반환되었습니다.")))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

}
