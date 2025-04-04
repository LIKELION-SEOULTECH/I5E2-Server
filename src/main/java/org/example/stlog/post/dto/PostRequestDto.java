package org.example.stlog.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {  // 게시글 생성 및 수정에 필요한 데이터를 담을 DTO
    private String password;
    private String title;
    private String content;
}
