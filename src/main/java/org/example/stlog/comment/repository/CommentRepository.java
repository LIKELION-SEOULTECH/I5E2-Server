package org.example.stlog.comment.repository;

import org.example.stlog.comment.entity.Comment;
import org.example.stlog.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Page<Comment> findByPost(Post post, Pageable pageable);
}