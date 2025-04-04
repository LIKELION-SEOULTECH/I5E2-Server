package org.example.stlog.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {  // 댓글 생성용
    private Long postId;
    private String content;
    private String commentPassword;
}