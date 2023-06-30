package com.sparta.spartaboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponseDto {
    private int statusCode;
    private String message;
}
