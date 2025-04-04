package org.example.stlog.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.stlog.post.entity.Post;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;  // JPA의 연관관계 매핑을 위해

    private String content;

    private String commentPassword;  // 비밀번호 (해시된 값)

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Comment(Post post, String content, String commentPassword) {
        this.post = post;  // // postId가 아니라 Post 객체
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