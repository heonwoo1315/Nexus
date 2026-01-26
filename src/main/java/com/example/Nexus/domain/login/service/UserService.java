package com.example.Nexus.domain.login.service;

import com.example.Nexus.domain.login.dto.object.User;
import com.example.Nexus.domain.login.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 로그인 로직
     * @return 로그인 성공 시 User 객체 반환
     * IllegalArgumentException 아이디가 없거나 비밀번호가 틀린 경우
     */
    public User login(String userId, String password) {
        // 1. 아이디로 유저 조회
        User user = userMapper.findByUserId(userId);

        // 2. 유저가 존재하지 않거나 비밀번호가 일치하지 않으면 예외 발생
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 성공 시 유저 정보 반환
        return user;
    }
}
