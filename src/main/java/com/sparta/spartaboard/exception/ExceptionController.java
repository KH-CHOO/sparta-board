package com.sparta.spartaboard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.status(401).body(new ExceptionResponse(401 ,e.getMessage()));
    }
}
