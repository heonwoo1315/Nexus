package com.example.Nexus.domain.board.dto.object;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private Integer hits; // int -> Integer로 변경 그래야 데이터가 없을 때 null을 허용하고, 나중에 DB에서 0으로 자연스럽게 치환 가능
    // PostControllerTest클래스의 createPostApiTest()에서 보내는 JSON에 hits가 없으므로 null로 넣어주는데 여기서 자료형 int는 null을 수용 불가능
    private LocalDateTime createdAt;
}
