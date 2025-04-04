package org.example.stlog.post.dto;

import lombok.Getter;
import org.example.stlog.post.entity.Post;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class PostPagingResponseDto {
    private final List<Post> posts;
    private final int totalPages;
    private final int currentPage;
    private final long totalElements;

    public PostPagingResponseDto(Page<Post> postPage) {
        this.posts = postPage.getContent();
        this.totalPages = postPage.getTotalPages();
        this.currentPage = postPage.getNumber();
        this.totalElements = postPage.getTotalElements();
    }
}
