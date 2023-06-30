package com.sparta.spartaboard.domain.dto;

import com.sparta.spartaboard.domain.entity.Post;
import com.sparta.spartaboard.domain.entity.User;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @Size(min=1,max=20, message = "제목은 2자 이상 20자 이하만 가능합니다.")
    private String title;
    @Size(min=1, message = "내용이 없습니다.")
    private String contents;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .user(user)
                .contents(contents)
                .build();
    }
}
