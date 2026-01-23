package com.example.Nexus.domain.board.controller;

import com.example.Nexus.domain.board.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    @WithMockUser
    @DisplayName("Post /api/posts 요청 시 게시글이 정상 저장되어야 한다")
    void createPostApiTest() throws Exception {
        //Given
        String json = "{\"title\":\"TDD 테스트 제목입니다\", \"content\":\"테스트 내용입니다\"}";

        // When & Then
        mockMvc.perform(post("/api/posts")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
}
