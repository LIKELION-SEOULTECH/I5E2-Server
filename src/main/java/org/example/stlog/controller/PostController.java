package org.example.stlog.controller;

import lombok.RequiredArgsConstructor;
import org.example.stlog.entity.Post;
import org.example.stlog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public Post createPost(@RequestParam String password,
                           @RequestParam String title,
                           @RequestParam String content) {
        return postService.createPost(password, title, content);
    }

    // 모든 게시글 조회
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPost();
    }

    // 특정 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,
                                           @RequestParam String password,
                                           @RequestParam String title,
                                           @RequestParam String content) {
        Optional<Post> updatedPost = postService.updatePost(postId, password, title, content);

        return updatedPost
                .map(ResponseEntity::ok)  // 수정된 게시글 반환
                .orElse(ResponseEntity.status(403).build());
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,
                                             @RequestParam String password) {
        boolean deleted = postService.deletePost(postId, password);
        return deleted ? ResponseEntity.ok("게시글 삭제 완료")
                : ResponseEntity.status(403).body("비밀번호가 틀렸거나 게시글이 존재하지 않습니다.");
    }
}