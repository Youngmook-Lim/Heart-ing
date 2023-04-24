package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.request.SendMessageReq;
import com.chillin.hearting.api.service.MessageService;
import com.chillin.hearting.db.domain.User;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    public void failSendMessage_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages";
        HttpServletRequest req = mock(HttpServletRequest.class);
        User user = User.builder().id("otherSender").build();
        when(req.getAttribute("user")).thenReturn(user);

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
        resultActions.andExpect(status().isUnauthorized());

    }


}
