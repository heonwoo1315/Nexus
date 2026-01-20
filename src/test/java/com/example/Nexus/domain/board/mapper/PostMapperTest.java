package com.example.Nexus.domain.board.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import com.example.Nexus.domain.board.dto.object.Post;

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
                .build();

        // When
        postMapper.insert(post); // 아직 존재하지 않는 메서드

        // Then
        assertThat(post.getId()).isNotNull();
    }
}
