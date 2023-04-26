package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.service.UserService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.ControllerExceptionHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private Gson gson;

    private String userId = "jj";
    private String nickname = "wjdwn";

    private User user = User.builder().id(userId).nickname(nickname).build();

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

//    @DisplayName("회원이 존재하지 않음.")
//    @Test
//    public void updateNickname() throws Exception {
//        // given
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        UpdateNicknameReq updateNicknameReq = UpdateNicknameReq.builder().id("비밀").nickname("졸려요").build();
//        final String url = "/api/v1/auth/users/nickname";
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.patch(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(gson.toJson(updateNicknameReq))
//                        .with(req -> {
//                            req.setAttribute("user", user);
//                            return req;
//                        })
//        );
//
//        // then
//        resultActions.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.message", is("계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.")));
//
//    }


}
