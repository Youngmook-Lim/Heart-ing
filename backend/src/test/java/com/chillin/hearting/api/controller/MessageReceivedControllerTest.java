package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.ReceivedMessageData;
import com.chillin.hearting.api.service.MessageReceivedService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.ControllerExceptionHandler;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class MessageReceivedControllerTest {

    @InjectMocks
    private MessageReceivedController messageReceivedController;

    @Mock
    private MessageReceivedService messageReceivedService;

    private MockMvc mockMvc;
    private Gson gson;

    private static final String SUCCESS = "success";

    private final long messageId = 0L;
    private final long heartId = 0L;
    private final long reportId = 0L;
    private final long emojiId = 0L;

    private final String content = "Test content";

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(messageReceivedController).setControllerAdvice(new ControllerExceptionHandler()).build();
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


}
