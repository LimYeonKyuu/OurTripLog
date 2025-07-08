package org.example.backend.base.exception.controller;

import org.example.backend.base.dto.ExceptionResponse;
import org.example.backend.base.exception.DoNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionController {

  @ExceptionHandler(DoNotExistException.class)
  public ResponseEntity<ExceptionResponse> handleDoNotExistException(DoNotExistException e) {
    ExceptionResponse response =
        ExceptionResponse.builder()
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(e.getMessage())
            .build();
    return ResponseEntity.badRequest().body(response);
  }
}
