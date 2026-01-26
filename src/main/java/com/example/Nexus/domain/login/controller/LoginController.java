package com.example.Nexus.domain.login.controller;

import com.example.Nexus.domain.login.dto.object.User;
import com.example.Nexus.domain.login.dto.request.LoginRequest;
import com.example.Nexus.domain.login.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpSession session) {
        // 1. 서비스 로직 호출 (아이디/비번 검증)
        User user = userService.login(request.getUserId(), request.getPassword());

        // 2. 검증 성공 시 세션에 유저 정보 저장
        // 서버 메모리에 저장되며, 클라이언트에게는 JSESSIONID 쿠키가 전달
        session.setAttribute("user", user);
    }
}
