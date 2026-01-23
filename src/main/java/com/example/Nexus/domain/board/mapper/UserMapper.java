package com.example.Nexus.domain.board.mapper;

import com.example.Nexus.domain.board.dto.object.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 유저 ID로 사용자 정보 조회
    User findByUserId(String userId);
}
