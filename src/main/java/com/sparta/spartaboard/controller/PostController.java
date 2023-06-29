package com.sparta.spartaboard.controller;

import com.sparta.spartaboard.domain.dto.PostRequestDto;
import com.sparta.spartaboard.domain.dto.PostResponseDto;
import com.sparta.spartaboard.domain.entity.User;
import com.sparta.spartaboard.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.createPost(postRequestDto, user);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.editPost(id, postRequestDto, user);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return postService.deletePost(id, user);
    }
}