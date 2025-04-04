package org.example.stlog.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.stlog.comment.dto.*;
import org.example.stlog.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,  // URL Path -> RESTful한 설계
            @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.createComment(postId, dto));
    }

    // 댓글 조회 (페이징 포함)
    @PostMapping("/posts/{postId}/comments/page")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPage(
            @PathVariable Long postId,  // URL Path -> RESTful한 설계
            @RequestBody CommentPageRequestDto dto) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId, dto.getPage(), dto.getSize()));
    }

    // 댓글 수정
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long postId,  // URL Path -> RESTful한 설계
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto dto) {
        return commentService.updateComment(commentId, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // 댓글 삭제
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long postId,  // URL Path -> RESTful한 설계
            @PathVariable Long commentId,
            @RequestBody CommentDeleteDto dto) {
        boolean deleted = commentService.deleteComment(commentId, dto.getCommentPassword());
        if (deleted) {
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 틀렸거나 댓글이 존재하지 않습니다.");
        }
    }
}