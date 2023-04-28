package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.SendMessageData;
import com.chillin.hearting.api.request.ReportReq;
import com.chillin.hearting.api.request.SendMessageReq;
import com.chillin.hearting.api.service.MessageService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.*;
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
    private static final String SUCCESS = "success";
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
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @Test
    public void failDeleteMessage_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId;

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is(UnAuthorizedException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failDeleteMessage_ServerFail() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId;
        User user = User.builder().id("receiver").build();
        doReturn(true).when(messageService).deleteMessage(messageId, "receiver");

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
        resultActions.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message", is(DeleteMessageFailException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failDeleteMessage_UnauthorizedUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId;
        User user = User.builder().id("sender").build();
        doThrow(new UnAuthorizedException("본인에게 온 메시지만 삭제할 수 있습니다."))
                .when(messageService).deleteMessage(messageId, "sender");

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
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is("본인에게 온 메시지만 삭제할 수 있습니다.")));
    }

    @Test
    public void failDeleteMessage_AlreadyDeleted() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId;
        User user = User.builder().id("sender").build();
        doThrow(new MessageAlreadyDeletedException())
                .when(messageService).deleteMessage(messageId, "sender");

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
        resultActions.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is(MessageAlreadyDeletedException.DEFAULT_MESSAGE)));
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
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @Test
    public void failReportMessage_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/reports";
        ReportReq reportReq = ReportReq.builder()
                .content(content)
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(reportReq))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is(UnAuthorizedException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failReportMessage_ServerFail() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/reports";
        User user = User.builder().id("sender").build();
        ReportReq reportReq = ReportReq.builder()
                .content(content)
                .build();
        doReturn(null).when(messageService).reportMessage(messageId, "sender", content);

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
        resultActions.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message", is(ReportFailException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failReportMessage_UnauthorizedUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/reports";
        User user = User.builder().id("sender").build();
        ReportReq reportReq = ReportReq.builder()
                .content(content)
                .build();
        doThrow(new UnAuthorizedException("본인이 받은 메시지만 신고할 수 있습니다."))
                .when(messageService).reportMessage(messageId, "sender", content);

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
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is("본인이 받은 메시지만 신고할 수 있습니다.")));
    }

    @Test
    public void failReportMessage_AlreadyReported() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/reports";
        User user = User.builder().id("sender").build();
        ReportReq reportReq = ReportReq.builder()
                .content(content)
                .build();
        doThrow(new MessageAlreadyReportedException())
                .when(messageService).reportMessage(messageId, "sender", content);

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
        resultActions.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is(MessageAlreadyReportedException.DEFAULT_MESSAGE)));
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
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @Test
    public void failAddEmoji_InvalidUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/emojis/" + emojiId;

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is(UnAuthorizedException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failAddEmoji_ServerFail() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/emojis/" + emojiId;
        User user = User.builder().id("sender").build();
        doReturn(null).when(messageService).addEmoji(messageId, "sender", emojiId);

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
        resultActions.andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message", is(EmojiFailException.DEFAULT_MESSAGE)));
    }

    @Test
    public void failAddEmoji_UnauthorizedUser() throws Exception {
        // given
        final String url = "/api/v1/messages/" + messageId + "/emojis/" + emojiId;
        User user = User.builder().id("sender").build();
        doThrow(new UnAuthorizedException("본인이 받은 메시지에만 이모지를 달 수 있습니다."))
                .when(messageService).addEmoji(messageId, "sender", emojiId);

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
        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is("본인이 받은 메시지에만 이모지를 달 수 있습니다.")));
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
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

}
