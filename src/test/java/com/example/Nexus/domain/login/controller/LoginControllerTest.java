package com.example.Nexus.domain.login.controller;

import com.example.Nexus.domain.board.dto.object.User;
import com.example.Nexus.domain.login.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class) // 아직 클래스가 없으므로 Red!
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("로그인 성공 시 세션에 유저 정보가 저장된다")
    void loginSessionTest() throws Exception {
        // Given
        User user = User.builder().userId("userA").username("홍길동").build();
        given(userService.login("userA", "password123")).willReturn(user);

        String json = "{\"userId\":\"userA\", \"password\":\"password123\"}";
        MockHttpSession session = new MockHttpSession(); // 가짜 세션 준비

        // When
        mockMvc.perform(post("/api/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session) // 요청에 세션 포함
                        .with(csrf()))
                .andExpect(status().isOk());

        // Then
        // 세션에 "user"라는 키로 유저 객체가 잘 들어갔는지 검증
        Object sessionUser = session.getAttribute("user");
        assertThat(sessionUser).isNotNull();
        assertThat(((User) sessionUser).getUserId()).isEqualTo("userA");
    }
}
