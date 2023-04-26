package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.UpdateNicknameData;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String userId = "userId";

    private final String nickname = "nickname";

    private final User user = User.builder().id("userId").nickname("testtest").build();

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

    @Test
    public void 사용자가_존재하지_않아서_updateNickname_실패() {
        // given
        doReturn(Optional.empty()).when(userRepository).findById(userId);

        // when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.updateNickname(userId, nickname));

        assertEquals(userNotFoundException.getMessage(), "해당 유저를 찾을 수 없습니다.");


    }

    @Test
    public void successUpdateNickname() {
        // given
        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        
        // when
        final UpdateNicknameData updateNicknameData = userService.updateNickname(userId, nickname);

        // then
        assertThat(updateNicknameData.getNickname()).isEqualTo("nickname");

        // verify
        verify(userRepository, times(1)).findById(userId);
    }

}
