package org.example.stlog.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long postId;  // 댓글이 달린 게시글 ID

    private String content;

    private String commentPassword;  // 비밀번호 (해시된 값)

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long postId, String content, String commentPassword) {
        this.postId = postId;
        this.content = content;
        this.commentPassword = commentPassword;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}