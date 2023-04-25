package com.chillin.hearting.api.service;

import com.chillin.hearting.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void kakaoLoginService() throws Exception {
        // fail code 먼저 적기 아래 주석 말고
        // given


        //when
//        String userId = userService.insertUser("abc", "KAAKO", "wjdwn@wjdwn.com", "jj");

//        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

//        assertThat(user.getId()).isEqualTo("abc");
//        assertThat(user.getType()).isEqualTo("KAAKO");
//        assertThat(user.getEmail()).isEqualTo("wjdwn@wjdwn.com");
//        assertThat(user.getNickname()).isEqualTo("jj");
//        assertThat(user.getStatus()).isEqualTo('A');

    }

}
