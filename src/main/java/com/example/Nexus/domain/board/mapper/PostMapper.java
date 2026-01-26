package com.example.Nexus.domain.board.mapper;

import com.example.Nexus.domain.board.dto.object.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostMapper {
    void insert(Post post);
    List<Post> findAll(@Param("offset") int offset, @Param("size") int size);
    Post findById(Long id);
    void updateHits(Long id);
    void update(Post post);
    void delete(Long id);
}
