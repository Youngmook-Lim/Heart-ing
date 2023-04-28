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
class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String userId = "userId";

    private final String nickname = "nickname";

    private final User user = User.builder().id("userId").nickname("testtest").build();

    @Test
    void 사용자가_존재하지_않아서_updateNickname_실패() {
        // given
        doReturn(Optional.empty()).when(userRepository).findById(userId);

        // when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.updateNickname(userId, nickname));

        assertEquals("해당 유저를 찾을 수 없습니다.", userNotFoundException.getMessage());


    }

    @Test
    void successUpdateNickname() {
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
