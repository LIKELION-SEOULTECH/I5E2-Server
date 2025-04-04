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
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.createComment(dto));
    }

    // 게시글에 달린 댓글 조회(페이징)
    @PostMapping("/page")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPage(@RequestBody CommentPageRequestDto dto) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(dto.getPostId(), dto.getPage(), dto.getSize()));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                @RequestBody CommentUpdateDto dto) {
        return commentService.updateComment(commentId, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @RequestBody CommentDeleteDto commentDeleteDto) {
        boolean deleted = commentService.deleteComment(commentId, commentDeleteDto.getCommentPassword());
        if (deleted) {
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 틀렸거나 댓글이 존재하지 않습니다.");
        }
    }
}