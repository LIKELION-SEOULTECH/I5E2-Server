package org.example.stlog.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPagingRequestDto {
    private Integer page;
    private Integer size;

    public int getPage() {
        return (page != null) ? page : 0;
    }

    public int getSize() {
        return (size != null) ? size : 10;
    }
}