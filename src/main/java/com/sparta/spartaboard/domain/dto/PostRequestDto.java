package com.sparta.spartaboard.domain.dto;

import com.sparta.spartaboard.domain.entity.Post;
import com.sparta.spartaboard.domain.entity.User;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String contents;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .user(user)
                .contents(contents)
                .build();
    }
}
