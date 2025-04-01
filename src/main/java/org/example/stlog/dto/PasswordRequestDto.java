package org.example.stlog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordRequestDto {  // 게시글 삭제 시 비밀번호만 필요한 경우를 위한 DTO
    private String password;
}
