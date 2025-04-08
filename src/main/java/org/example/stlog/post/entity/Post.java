package org.example.stlog.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.stlog.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String username;
    private String emotion;
    private String password;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 게시글 생성자
    @Builder
    public Post(String username, String emotion, String password, String title, String content) {
        this.username = username;
        this.emotion = emotion;
        this.password = password;  // 이미 해싱된 값이 들어옴
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 게시글 수정 기능
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}