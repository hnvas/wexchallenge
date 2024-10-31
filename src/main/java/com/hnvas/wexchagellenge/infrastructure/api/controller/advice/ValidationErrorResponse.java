package com.hnvas.wexchagellenge.infrastructure.api.controller.advice;

import java.util.Map;

import com.hnvas.wexchagellenge.application.exception.ValidationException;

public record ValidationErrorResponse(String message, Map<String, String> violations) {

  public static ValidationErrorResponse from(ValidationException exception) {
    return new ValidationErrorResponse(exception.getMessage(), exception.getViolations());
  }
}
