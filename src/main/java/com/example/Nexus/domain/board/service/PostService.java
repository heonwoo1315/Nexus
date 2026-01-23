package com.example.Nexus.domain.board.service;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // PostMapper를 주입받기 위한 생성자 생성
public class PostService {

    private final PostMapper postMapper;

    public void write(Post post) {
        if (post.getTitle() == null || post.getTitle().length() < 5) {
            if (post.getTitle() == null || post.getTitle().length() < 5) {
                throw new IllegalArgumentException("제목은 최소 5글자 이상이어야 한다.");
            }
            // 비즈니스 로직: 매퍼를 호출하여 저장함
            postMapper.insert(post);
        }
    }
}
