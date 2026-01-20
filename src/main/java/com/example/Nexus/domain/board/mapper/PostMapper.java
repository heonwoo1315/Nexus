package com.example.Nexus.domain.board.mapper;

import com.example.Nexus.domain.board.dto.object.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void insert(Post post);
}
