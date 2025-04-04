package org.example.stlog.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String content;
}