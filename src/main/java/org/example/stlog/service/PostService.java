package org.example.stlog.service;

import lombok.RequiredArgsConstructor;
import org.example.stlog.entity.Post;
import org.example.stlog.repository.PostRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service  // 이 클래스가 서비스 역할을 하는 것을 알려줌
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;  // SecurityConfig에서 주입받음

    // 게시글 생성
    public Post createPost(String password, String title, String content) {
        String encodedPassword = passwordEncoder.encode(password);  // 비밀번호 해싱
        Post post = Post.builder()
                .password(encodedPassword)
                .title(title)
                .content(content)
                .build();
        return postRepository.save(post);
    }

    // 모든 게시글 조회
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    // 특정 게시글 조회
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 게시물 수정 (비밀번호 확인 후 수정)
    @Transactional
    public Optional<Post> updatePost(Long postId, String password, String title, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 비밀번호 검증
            if (passwordEncoder.matches(password, post.getPassword())) {
                post.update(title, content);
                return Optional.of(postRepository.save(post));
            }
        }
        return Optional.empty();  // 비밀번호 불일치 or 게시글이 존재X
    }

    // 게시글 삭제 (비밀번호 확인 후 삭제)
    @Transactional
    public boolean deletePost(Long postId, String password) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 비밀번호 검증
            if (passwordEncoder.matches(password, post.getPassword())) {
                postRepository.delete(post);  // 삭제
                return true;
            }
        }
        return false;  // 비밀번호 불일치 or 게시글이 존재X
    }
}