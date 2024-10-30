package com.hnvas.wexchagellenge.application.validation;

import java.util.List;

public class ValidationException extends RuntimeException {

  private List<String> violations;

  public ValidationException(String message, List<String> violations) {
    super(message);
    this.violations = violations;
  }
}
