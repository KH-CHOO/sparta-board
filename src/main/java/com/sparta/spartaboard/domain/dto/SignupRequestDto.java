package com.sparta.spartaboard.domain.dto;

import lombok.Getter;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
public class SignupRequestDto {
    @Size(min=4,max=10,message = "username은 4자 이상 10자 이하만 가능합니다.")
    @Pattern(regexp = "[a-z0-9]+", message = "username에는 알파벳 소문자와 숫자만 사용할 수 있습니다.")
    private String username;

    @Size(min=8,max=15, message = "password는 8자 이상 15자 이하만 가능합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$" , message = "password에는 알파벳 대문자, 소문자, 숫자를 반드시 포함해야 합니다.")
    private String password;
}