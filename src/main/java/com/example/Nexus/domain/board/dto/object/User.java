package com.example.Nexus.domain.board.dto.object;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String userId;
    private String password;
    private String username;
}
