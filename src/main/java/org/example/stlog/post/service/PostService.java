package org.example.stlog.post.service;

import lombok.RequiredArgsConstructor;
import org.example.stlog.post.entity.Post;
import org.example.stlog.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service  // 이 클래스가 서비스 역할을 하는 것을 알려줌
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;  // SecurityConfig에서 주입받음

    // 페이징된 게시글 목록 조회
    public Page<Post> getPosts(int page, int size) {
        return postRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "postId")))
        );  // 최신순 정렬
    }

    // 게시글 생성
    public Post createPost(String username, String password, String content) {
        String encodedPassword = passwordEncoder.encode(password);  // 비밀번호 해싱
        Post post = Post.builder()
                .username(username)
                .password(encodedPassword)
                .content(content)
                .build(); // emotion은 일단 Null로 해놓을게요
        return postRepository.save(post);
    }

    // 없애도 되나?
//    // 모든 게시글 조회
//    public List<Post> getAllPost() {
//        return postRepository.findAll();
//    }

    // 특정 게시글 조회
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 게시물 수정 (비밀번호 확인 후 수정)
    @Transactional
    public Optional<Post> updatePost(Long postId, String password, String username, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 비밀번호 검증
            if (passwordEncoder.matches(password, post.getPassword())) {
                post.update(content);
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