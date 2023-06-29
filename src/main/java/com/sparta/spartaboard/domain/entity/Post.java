package com.sparta.spartaboard.domain.entity;

import com.sparta.spartaboard.domain.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name="username")
    private User user;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Builder
    public Post(String title, String writer, String password, String contents){
        this.title = title;
        this.contents = contents;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
