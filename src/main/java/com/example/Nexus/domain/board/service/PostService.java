package com.example.Nexus.domain.board.service;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // PostMapper를 주입받기 위한 생성자 생성
public class PostService {

    private final PostMapper postMapper;

    public void write(Post post) {
        if (post.getTitle() == null || post.getTitle().length() < 5) {
            throw new IllegalArgumentException("제목은 최소 5글자 이상이어야 한다.");
            // 비즈니스 로직: 매퍼를 호출하여 저장함
        }
        postMapper.insert(post);
    }

    @Transactional
    public Post getPost(Long id) {
        // 1. 조회수 1 증가
        postMapper.updateHits(id);

        // 2. 게시글 상세 정보 가져오기
        Post post = postMapper.findById(id);
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
        return post;
    }

    public List<Post> getList(int page) {
        // 페이지 번호를 이용해 DB의 offset을 계산합니다. (페이지당 10개 기준)
        int validPage = Math.max(1, page);
        int size = 10;
        int offset = (validPage - 1) * size;
        return postMapper.findAll(offset, size);
    }

    public void updatePost(Long id, Post updateParam, String currentUserId) {
        Post post = postMapper.findById(id);

        // 1. 존재 확인
        if (post == null) throw new IllegalArgumentException("글이 존재하지 않습니다.");

        // 2. 권한 확인: 작성자와 현재 로그인한 유저 ID 비교
        if (!post.getWriterId().equals(currentUserId)) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        // 3. 실제 수정 수행
        post.setTitle(updateParam.getTitle());
        post.setContent(updateParam.getContent());
        postMapper.update(post);
    }

    public void deletePost(Long id, String currentUserId) {
        Post post = postMapper.findById(id);
        if (post == null) throw new IllegalArgumentException("글이 존재하지 않습니다.");

        // 권한 확인
        if (!post.getWriterId().equals(currentUserId)) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        postMapper.delete(id);
    }

}
