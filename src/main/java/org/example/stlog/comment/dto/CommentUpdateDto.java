package org.example.stlog.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDto {  // 댓글 수정용
    private String content;
    private String commentPassword;
}