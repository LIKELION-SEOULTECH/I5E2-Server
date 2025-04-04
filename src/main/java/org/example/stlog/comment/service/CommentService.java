package org.example.stlog.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.stlog.comment.dto.CommentRequestDto;
import org.example.stlog.comment.dto.CommentResponseDto;
import org.example.stlog.comment.dto.CommentUpdateDto;
import org.example.stlog.comment.entity.Comment;
import org.example.stlog.comment.repository.CommentRepository;
import org.example.stlog.post.entity.Post;
import org.example.stlog.post.repository.PostRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    // 댓글 생성
    public CommentResponseDto createComment(Long postId, CommentRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        String encodedPassword = passwordEncoder.encode(dto.getCommentPassword());

        Comment comment = Comment.builder()
                .post(post)
                .content(dto.getContent())
                .commentPassword(encodedPassword)
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDto(saved.getCommentId(), saved.getPost().getPostId(), saved.getContent());
    }

    // ✅ 페이징된 댓글 목록 조회 (createdAt, commentId 기준 내림차순 정렬)
    public Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size) {
        PageRequest pageable = PageRequest.of(
                page, size,
                Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "commentId"))
        );

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        Page<Comment> commentPage = commentRepository.findByPost(post, pageable);

        return commentPage.map(c -> new CommentResponseDto(c.getCommentId(), postId, c.getContent()));
    }

    // 댓글 수정
    @Transactional
    public Optional<CommentResponseDto> updateComment(Long commentId, CommentUpdateDto dto) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if (passwordEncoder.matches(dto.getCommentPassword(), comment.getCommentPassword())) {
                comment.updateContent(dto.getContent());
                return Optional.of(new CommentResponseDto(comment.getCommentId(), comment.getPost().getPostId(), comment.getContent()));
            }
        }
        return Optional.empty();
    }

    // 댓글 삭제
    public boolean deleteComment(Long commentId, String password) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if (passwordEncoder.matches(password, comment.getCommentPassword())) {
                commentRepository.delete(comment);
                return true;
            }
        }
        return false;
    }
}
