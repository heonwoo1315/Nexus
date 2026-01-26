package com.example.Nexus.domain.board.controller;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.login.dto.object.User;
import com.example.Nexus.domain.board.service.PostService;
import com.example.Nexus.infrastructure.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody Post post, @LoginUser User user) {
        // 1. 로그인하지 않은 유저가 접근할 경우의 처리 (인프라/보안 로직)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. 게시글 객체에 작성자 정보 설정 (비즈니스 연결)
        post.setWriterId(user.getUserId());

        // 3. 저장
        postService.write(post);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Post> getList(@RequestParam(name = "page", defaultValue = "1") int page) {
        // @RequestParam의 defaultValue를 사용하면 페이지 번호가 없을 때 자동으로 1을 사용합니다.
        return postService.getList(page);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable(name = "id") Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable(name = "id") Long id,
                           @RequestBody Post post,
                           @LoginUser User user) {
        postService.updatePost(id, post, user.getUserId());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable(name = "id") Long id,
                           @LoginUser User user) {
        postService.deletePost(id, user.getUserId());
    }
}
