package org.example.stlog.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPageRequestDto {
    private Long postId;
    private int page;
    private int size;
}
