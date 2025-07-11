package org.example.backend.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionResponse {
  private String error;

  private String message;
}
