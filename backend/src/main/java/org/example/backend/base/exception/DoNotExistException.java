package org.example.backend.base.exception;

public class DoNotExistException extends RuntimeException {
  public DoNotExistException(String message) {
    super(message);
  }
}
