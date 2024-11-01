package com.hnvas.wexchagellenge.infrastructure.api.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.hnvas.wexchagellenge.configuration.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hnvas.wexchagellenge.application.exception.ResourceNotFoundException;
import com.hnvas.wexchagellenge.application.exception.ValidationException;

import jakarta.servlet.http.HttpServletRequest;

@UnitTest
class RestExceptionAdviceTest {

  private RestExceptionAdvice restExceptionAdvice;
  private HttpServletRequest request;

  @BeforeEach
  void setUp() {
    restExceptionAdvice = new RestExceptionAdvice();
    request = mock(HttpServletRequest.class);
  }

  @Test
  void handleValidationException_ShouldReturnUnprocessableEntity() {
    // Arrange
    Map<String, String> violations = Map.of("field", "error message");
    ValidationException exception = new ValidationException("Validation failed", violations);
    when(request.getMethod()).thenReturn("POST");

    // Act
    ResponseEntity<ValidationErrorResponse> response =
        restExceptionAdvice.handleValidationException(exception, request);

    // Assert
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Validation failed", response.getBody().message());
    assertEquals(violations, response.getBody().violations());
  }

  @Test
  void handleValidationException_ShouldReturnBadRequest() {
    // Arrange
    Map<String, String> violations = Map.of("field", "error message");
    ValidationException exception = new ValidationException("Validation failed", violations);
    when(request.getMethod()).thenReturn("GET");

    // Act
    ResponseEntity<ValidationErrorResponse> response =
        restExceptionAdvice.handleValidationException(exception, request);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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

  @Test
  void handleResourceNotFoundException_ShouldReturnNotFound() {
    // Arrange
    ResourceNotFoundException exception = ResourceNotFoundException.of("Purchase");

    // Act
    ResponseEntity<String> response =
        restExceptionAdvice.handleResourceNotFoundException(exception);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
