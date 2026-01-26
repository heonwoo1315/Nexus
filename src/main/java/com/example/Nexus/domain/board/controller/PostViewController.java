package com.example.Nexus.domain.board.controller;

import com.example.Nexus.domain.board.dto.object.Post;
import com.example.Nexus.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PostViewController {
    private final PostService postService;

    @GetMapping("/")
    public String index() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        // 1. 서비스에서 게시글 목록을 가져옵니다.
        List<Post> posts = postService.getList(page);

        // 2. 모델에 데이터를 담아 타임리프 템플릿으로 넘깁니다.
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);

        // 3. 반환값은 렌더링할 HTML 파일의 이름입니다.
        return "board/list";
    }

    @GetMapping("/posts/write")
    public String writeForm() {
        return "board/write"; // templates/board/write.html을 찾습니다.
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable(name = "id") Long id, Model model) {
        // getPost를 호출하면 조회수가 1 증가하고 게시글 정보를 가져옵니다.
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "board/detail";
    }

    @GetMapping("/posts/edit/{id}")
    public String editForm(@PathVariable(name = "id") Long id, Model model) {
        // 기존 글 정보를 가져와서 수정 폼에 채워줍니다.
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "board/edit"; // templates/board/edit.html
    }
}
