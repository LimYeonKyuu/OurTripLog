package org.example.backend.auth.exception.controller;

import org.example.backend.auth.exception.UnauthorizedException;
import org.example.backend.base.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionController {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(401).body(response);
    }
}
