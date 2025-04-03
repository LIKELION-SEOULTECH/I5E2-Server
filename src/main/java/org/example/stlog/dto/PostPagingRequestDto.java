package org.example.stlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPagingRequestDto {
    private int page = 0;
    private int size = 10;
}