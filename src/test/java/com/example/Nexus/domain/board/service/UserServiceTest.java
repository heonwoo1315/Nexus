package com.example.Nexus.domain.board.service;

import com.example.Nexus.domain.board.dto.object.User;
import com.example.Nexus.domain.login.mapper.UserMapper;
import com.example.Nexus.domain.login.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService; // 아직 존재하지 않으므로 빨간 줄이 뜹니다.

    @Test
    @DisplayName("아이디와 비밀번호가 일치하면 로그인에 성공한다")
    void loginSuccessTest() {
        // Given
        User mockUser = User.builder()
                .userId("testUser")
                .password("password123")
                .username("테스터")
                .build();

        // 가짜 매퍼가 특정 아이디로 조회 시 위 객체를 반환하도록 설정
        given(userMapper.findByUserId("testUser")).willReturn(mockUser);

        // When
        User loggedInUser = userService.login("testUser", "password123");

        // Then
        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUserId()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("비밀번호가 다르면 로그인에 실패하여 예외가 발생한다")
    void loginFailTest() {
        // Given
        User mockUser = User.builder()
                .userId("testUser")
                .password("password123")
                .build();
        given(userMapper.findByUserId("testUser")).willReturn(mockUser);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("testUser", "wrong_password");
        });
    }
}
