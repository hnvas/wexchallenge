package com.hnvas.wexchagellenge.application.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

  private List<String> violations;

  public ValidationException(String message, List<String> violations) {
    super(message);
    this.violations = violations;
  }
}
