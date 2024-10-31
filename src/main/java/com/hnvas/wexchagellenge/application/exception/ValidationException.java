package com.hnvas.wexchagellenge.application.exception;

import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

  private final Map<String, String> violations;

  public ValidationException(String message, Map<String, String> violations) {
    super(message);
    this.violations = violations;
  }

  private static <T> Map<String, String> collectViolations(Set<ConstraintViolation<T>> violations) {
    return violations.stream()
        .collect(
            toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage));
  }

  public static <T> ValidationException from(String message, Set<ConstraintViolation<T>> violations) {
    return new ValidationException(message, collectViolations(violations));
  }
}