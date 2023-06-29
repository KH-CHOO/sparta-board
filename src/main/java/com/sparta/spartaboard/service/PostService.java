package com.sparta.spartaboard.service;

import com.sparta.spartaboard.domain.dto.PostRequestDto;
import com.sparta.spartaboard.domain.dto.PostResponseDto;
import com.sparta.spartaboard.domain.entity.Post;
import com.sparta.spartaboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return ResponseEntity.ok(postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList());
    }

    public ResponseEntity<PostResponseDto> createPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toEntity();

        Post savePost = postRepository.save(post);

        return ResponseEntity.ok(new PostResponseDto(savePost));
    }

    public ResponseEntity<PostResponseDto> getPost(Long id) {
        Post post = findPost(id);

        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @Transactional
    public ResponseEntity<PostResponseDto> editPost(Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);

        checkPassword(post.getPassword(), postRequestDto.getPassword());

        post.update(postRequestDto);

        return ResponseEntity.ok(new PostResponseDto(post));
    }

    public ResponseEntity<String> deletePost(Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);

        checkPassword(post.getPassword(), postRequestDto.getPassword());

        postRepository.delete(post);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

    private void checkPassword (String password, String enteredPassword) {
        if (!Objects.equals(password, enteredPassword)) throw new IllegalStateException("비밀번호가 일치하지 않습니다");
    }
}
