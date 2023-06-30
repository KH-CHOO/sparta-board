package com.sparta.spartaboard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse<T> {
    private int statusCode;
    private T error;
}
