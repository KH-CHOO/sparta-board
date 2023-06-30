package com.sparta.spartaboard.controller;

import com.sparta.spartaboard.domain.dto.CommonResponseDto;
import com.sparta.spartaboard.domain.dto.PostRequestDto;
import com.sparta.spartaboard.domain.dto.PostResponseDto;
import com.sparta.spartaboard.domain.entity.User;
import com.sparta.spartaboard.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 기능")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 목록 불러오기")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return postService.getPosts();
    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.createPost(postRequestDto, user);
    }

    @Operation(summary = "게시글 불러오기")
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.editPost(id, postRequestDto, user);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.deletePost(id, user);
    }
}