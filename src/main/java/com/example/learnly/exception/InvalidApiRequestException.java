package com.example.learnly.exception;

public class InvalidApiRequestException extends RuntimeException {
  public InvalidApiRequestException(String message) {
    super(message);
  }
}
