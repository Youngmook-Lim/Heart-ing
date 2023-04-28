package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.service.MessageInboxService;
import com.chillin.hearting.db.domain.User;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MessageInboxControllerTest {

    @InjectMocks
    private MessageInboxController messageInboxController;

    @Mock
    private MessageInboxService messageInboxService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageInboxController)
                .build();
    }

    @Test
    void 메시지영구저장() throws Exception {

        // given
        final String url = "/api/v1/messages/inbox/1";
        User user = User.builder().id("otherSender").build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void 영구보관메시지조회() throws Exception {

        // given
        final String url = "/api/v1/messages/inbox";
        User user = User.builder().id("otherSender").build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                                    request.setAttribute("user", user);
                                    return request;
                                }
                        )
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 영구보관메시지상세조회() throws Exception {
        // given
        final String url = "/api/v1/messages/inbox/1";
        User user = User.builder().id("otherSender").build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                                    request.setAttribute("user", user);
                                    return request;
                                }
                        )
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 메시지영구삭제() throws Exception {

        // given
        final String url = "/api/v1/messages/inbox/1";
        User user = User.builder().id("otherSender").build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }
}
