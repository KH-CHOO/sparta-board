package com.sparta.spartaboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private int statusCode;
    private String message;
}
