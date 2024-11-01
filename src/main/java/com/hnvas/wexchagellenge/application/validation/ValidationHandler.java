package com.hnvas.wexchagellenge.application.validation;

import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class ValidationHandler<T> {

  private Validator validator;
  private Set<ConstraintViolation<T>> violations;

  public ValidationHandler() {
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      this.validator = validatorFactory.getValidator();
    }
  }

  public boolean isValid(T object) {
    violations = validator.validate(object);
    return violations.isEmpty();
  }

  public Set<ConstraintViolation<T>> violations() {
    return violations;
  }
}
