package com.example.Nexus.domain.board.service;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Mockito 사용 설정
class PostServiceTest {

    @Mock
    private PostMapper postMapper; // 가짜 객체 생성

    @InjectMocks
    private PostService postService; // 가짜 객체를 주입받을 서비스

    @Test
    @DisplayName("게시글 작성 서비스 로직 테스트")
    void createPostTest() {
        // Given
        Post post = Post.builder()
                .title("서비스 테스트 제목")
                .content("서비스 테스트 내용")
                .build();

        // When
        postService.write(post);

        // Then
        // postMapper의 insert 메서드가 실제로 한 번 호출되었는지 검증
        verify(postMapper).insert(any(Post.class));
    }

    @Test
    @DisplayName("제목이 5글자 미만이면 예외가 발생한다")
    void createPostFailTest() {
        // Given
        Post post = Post.builder()
                .title("짧은")
                .content("내용")
                .build();

        // When & Then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            postService.write(post);
        });
    }
}
