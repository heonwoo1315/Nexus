package com.example.Nexus.domain.board.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import com.example.Nexus.domain.board.dto.object.Post;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostMapperTest {

    @Autowired
    private PostMapper postMapper; // 현재 이 클래스는 존재하지 않으므로 빨간줄이 떠야함

    @Test
    @DisplayName("게시글을 저장하면 ID가 생성되어야 한다")
    void savePostTest() {

        // Given
        Post post = Post.builder()
                .title("TDD 시작하기")
                .content("MyBatis로 TDD Red 단계를 진행 중입니다.")
                .writerId("userA")
                .build();

        // When
        postMapper.insert(post); // 아직 존재하지 않는 메서드

        // Then
        assertThat(post.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글을 10개씩 페이징하여 조회한다")
    void selectPostListPagingTest() {
        // Given: 테스트용 데이터 15개 저장 (반복분 활용)
        for (int i = 0; i <= 15; i++){
            Post post = Post.builder()
                    .title("테스트 제목 " + i)
                    .content("내용 " + i)
                    .writerId("userA")
                    .build();
            postMapper.insert(post);
        }

        // When: 1페이지(0~10개) 조회 요청
        // offset: 시작 위치, size: 가져올 개수
        List<Post> posts = postMapper.findAll(0, 10);

        // Then
        assertThat(posts.size()).isEqualTo(10); // 10개만 가져왔는지
        assertThat(posts.get(0).getTitle()).contains("15"); // 최신순 정렬 확인 (가장 마지막에 넣은 게 15번)
    }
}
