package com.hnvas.wexchagellenge.infrastructure.api.controller.advice;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hnvas.wexchagellenge.application.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestExceptionAdviceTest {

  private RestExceptionAdvice restExceptionAdvice;

  @BeforeEach
  void setUp() {
    restExceptionAdvice = new RestExceptionAdvice();
  }

  @Test
  void handleValidationException_ShouldReturnUnprocessableEntity() {
    // Arrange
    Map<String, String> violations = Map.of("field", "error message");
    ValidationException exception = new ValidationException("Validation failed", violations);

    // Act
    ResponseEntity<ValidationErrorResponse> response =
        restExceptionAdvice.handleValidationException(exception);

    // Assert
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Validation failed", response.getBody().message());
    assertEquals(violations, response.getBody().violations());
  }

  @Test
  void handleException_ShouldReturnInternalServerError() {
    // Arrange
    Exception exception = new Exception("Internal server error");

    // Act
    ResponseEntity<String> response = restExceptionAdvice.handleException(exception);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Internal server error", response.getBody());
  }
}
