package com.sparta.spartaboard.domain.dto;

import com.sparta.spartaboard.domain.entity.Post;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String writer;
    private String password;
    private String contents;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .writer(writer)
                .password(password)
                .contents(contents)
                .build();
    }
}
