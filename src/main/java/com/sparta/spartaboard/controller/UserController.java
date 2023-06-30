package com.sparta.spartaboard.controller;

import com.sparta.spartaboard.domain.dto.UserRequestDto;
import com.sparta.spartaboard.domain.dto.UserResponseDto;
import com.sparta.spartaboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserRequestDto userRequestDto, HttpServletResponse res) {
        return userService.signup(userRequestDto, res);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse res) {
        return userService.login(userRequestDto, res);
    }



}
