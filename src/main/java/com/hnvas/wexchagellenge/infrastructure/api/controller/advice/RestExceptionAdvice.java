package com.hnvas.wexchagellenge.infrastructure.api.controller.advice;

import com.hnvas.wexchagellenge.application.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hnvas.wexchagellenge.application.exception.ValidationException;

@ControllerAdvice
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(
      ValidationException exception) {
    return ResponseEntity.unprocessableEntity().body(ValidationErrorResponse.from(exception));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception exception) {
    return ResponseEntity.internalServerError().body(exception.getMessage());
  }
}
