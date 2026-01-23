package com.example.Nexus.domain.board.controller;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody Post post) {
        postService.write(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
