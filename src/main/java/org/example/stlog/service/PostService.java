package org.example.stlog.service;

import lombok.RequiredArgsConstructor;
import org.example.stlog.entity.Post;
import org.example.stlog.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service  // 이 클래스가 서비스 역할을 하는 것을 알려줌
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 모든 게시글 조회
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    // 특정 게시글 조회
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 게시글 생성
    public Post createPost(String password, String title, String content) {
        Post post = Post.builder()
                .password(password)
                .title(title)
                .content(content)
                .build();
        return postRepository.save(post);
    }

    // 게시물 수정 (비밀번호 확인 후 수정)
    @Transactional
    public Optional<Post> updatePost(Long postId, String password, String title, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // 비밀번호 검증
            if (post.checkPassword(password)) {
                post.update(title, content);
                return Optional.of(post);
            } else {
                return Optional.empty();  // 비밀번호 틀리면 수정 불가
            }
        }
        return Optional.empty();  // 게시글이 존재X
    }

    // 게시글 삭제 (비밀번호 확인 후 삭제)
    @Transactional
    public boolean deletePost(Long postId, String password) {
        return postRepository.findById(postId)
                .filter(post -> post.checkPassword(password))  // 비밀번호 일치 확인
                .map(post -> {
                    postRepository.delete(post);
                    return true;
                })
                .orElse(false);
    }
}