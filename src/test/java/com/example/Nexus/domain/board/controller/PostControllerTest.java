package com.example.Nexus.domain.board.controller;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.example.Nexus.domain.login.dto.object.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // put 메서드용
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @TestConfiguration
    static class TestConfig implements WebMvcConfigurer {
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new HandlerMethodArgumentResolver() {
                @Override
                public boolean supportsParameter(MethodParameter parameter) {
                    // @LoginUser 어노테이션이 붙어 있는지 확인
                    return parameter.hasParameterAnnotation(com.example.Nexus.infrastructure.annotation.LoginUser.class);
                }

                @Override
                public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                    // 테스트에 사용할 가짜 유저 객체 반환
                    User testUser = new User();
                    testUser.setUserId("test_heonwoo"); // 헌우님의 유저 ID 세팅
                    return testUser;
                }
            });
        }
    }

    @Test
    @WithMockUser
    @DisplayName("Post /api/posts 요청 시 게시글이 정상 저장되어야 한다")
    void createPostApiTest() throws Exception {
        // 실제로는 인터셉터나 리졸버가 세션의 User를 넘겨주지만,
        // 여기서는 컨트롤러가 user.getUserId()를 호출하므로 Mocking이 필요할 수 있다.
        String json = "{\"title\":\"TDD 테스트 제목입니다\", \"content\":\"테스트 내용입니다\"}";

        // When & Then
        mockMvc.perform(post("/api/posts")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("Get /api/posts 요청 시 1페이지 목록을 반환한다")
    void getPostApiTest() throws Exception {
        // Given
        List<Post> mockPosts = IntStream.range(0, 5)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .writerId("userA")
                        .build()) // 여기서 괄호를 닫고
                .collect(Collectors.toList()); // collect를 Stream 레벨에서 호출해야 합니다.

        given(postService.getList(1)).willReturn(mockPosts);

        // When & Then
        mockMvc.perform(get("/api/posts")
                        .param("page", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].title").value("제목 0"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/posts/{id} 요청 시 게시글 상세 정보를 반환한다")
    void getPostDetailTest() throws Exception {
        // Given
        Long postId = 1L;
        Post mockPost = Post.builder()
                .id(postId)
                .title("상세 보기 제목")
                .content("상세 내용입니다")
                .writerId("test_user")
                .build();

        given(postService.getPost(postId)).willReturn(mockPost);

        // When & Then
        mockMvc.perform(get("/api/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("상세 보기 제목"))
                .andExpect(jsonPath("$.content").value("상세 내용입니다"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/posts/{id} 요청 시 게시글 상세 정보를 반환한다")
    void getPostDetailApiTest() throws Exception {
        // Given
        Long postId = 1L;
        Post mockPost = Post.builder()
                .id(postId)
                .title("상세 보기 제목")
                .content("상세 내용입니다")
                .writerId("test_heonwoo")
                .hits(1)
                .build();

        given(postService.getPost(postId)).willReturn(mockPost);

        // When & Then
        mockMvc.perform(get("/api/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("상세 보기 제목"))
                .andExpect(jsonPath("$.hits").value(1));
    }

    @Test
    @WithMockUser
    @DisplayName("남의 글을 수정하려고 하면 500(또는 지정한 에러)이 발생한다")
    void updatePostSecurityTest() throws Exception {
        // Given: 작성자가 'other_user'인 게시글
        Long postId = 1L;
        String json = "{\"title\":\"해킹 시도\", \"content\":\"내용 변경\"}";

        // 서비스에서 IllegalStateException을 던지도록 설정
        doThrow(new IllegalStateException("수정 권한이 없습니다."))
                .when(postService).updatePost(eq(postId), any(Post.class), eq("test_heonwoo"));

        // When & Then
        mockMvc.perform(put("/api/posts/" + postId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError()); // 예외 처리기에 따라 다름
    }
}
