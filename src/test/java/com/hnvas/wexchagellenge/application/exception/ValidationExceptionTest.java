package com.hnvas.wexchagellenge.application.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class ValidationExceptionTest {

  @Test
  void testValidationException() {
    String message = "Validation failed";
    List<String> violations = List.of("Violation 1", "Violation 2");

    ValidationException exception = new ValidationException(message, violations);

    assertEquals(message, exception.getMessage());
    assertNotNull(exception.getViolations());
    assertEquals(violations.size(), exception.getViolations().size());
    assertEquals(violations, exception.getViolations());
  }
}