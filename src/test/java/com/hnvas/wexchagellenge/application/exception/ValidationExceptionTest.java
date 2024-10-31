package com.hnvas.wexchagellenge.application.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationExceptionTest {

  @Test
  void testValidationException() {
    String message = "Validation failed";
    Map<String, String> violations = Map.of("field1", "error message 1", "field2", "error message 2");

    ValidationException exception = new ValidationException(message, violations);

    assertEquals(message, exception.getMessage());
    assertNotNull(exception.getViolations());
    assertEquals(violations.size(), exception.getViolations().size());
    assertEquals(violations, exception.getViolations());
  }

  @Test
  void testFromMethod() {
    String message = "Validation failed";
    ConstraintViolation<Object> violation1 = Mockito.mock(ConstraintViolation.class);
    ConstraintViolation<Object> violation2 = Mockito.mock(ConstraintViolation.class);

    Path path1 = Mockito.mock(Path.class);
    Path path2 = Mockito.mock(Path.class);

    Mockito.when(path1.toString()).thenReturn("field1");
    Mockito.when(path2.toString()).thenReturn("field2");

    Mockito.when(violation1.getPropertyPath()).thenReturn(path1);
    Mockito.when(violation1.getMessage()).thenReturn("error message 1");
    Mockito.when(violation2.getPropertyPath()).thenReturn(path2);
    Mockito.when(violation2.getMessage()).thenReturn("error message 2");

    Set<ConstraintViolation<Object>> violations = Set.of(violation1, violation2);

    ValidationException exception = ValidationException.from(message, violations);

    assertEquals(message, exception.getMessage());
    assertNotNull(exception.getViolations());
    assertEquals(2, exception.getViolations().size());
    assertEquals("error message 1", exception.getViolations().get("field1"));
    assertEquals("error message 2", exception.getViolations().get("field2"));
  }
}