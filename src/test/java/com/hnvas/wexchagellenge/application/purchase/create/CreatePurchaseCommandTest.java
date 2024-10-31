package com.hnvas.wexchagellenge.application.purchase.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;

class CreatePurchaseCommandTest {

  private ValidationHandler<Object> validator;

  @BeforeEach
  void setUp() {
    this.validator = new ValidationHandler<>();
  }

  @Test
  void testValidCreatePurchaseCommand() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    assertEquals(0, validator.violations().size());
  }

  @Test
  void testToModel() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    Purchase purchase = command.toModel();

    assertEquals(command.description(), purchase.description());
    assertEquals(command.purchaseDate(), purchase.purchaseDate());
    assertEquals(command.amount(), purchase.amount());
  }

  @ParameterizedTest
  @MethodSource("invalidCommandProvider")
  void testInvalidCreatePurchaseCommand(
      CreatePurchaseCommand command, String expectedMessage, int expectedViolations) {
    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(expectedViolations, violations.size());
    assertTrue(violations.stream().map(ConstraintViolation::getMessage).anyMatch(expectedMessage::equals));
  }

  private static Stream<Arguments> invalidCommandProvider() {
    return Stream.of(
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("")
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("10.00"))
                .build(),
            "Description cannot be empty",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description(null)
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("10.00"))
                .build(),
            "Description is required",
            2),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("A".repeat(51))
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("10.00"))
                .build(),
            "Description must be between 20 and 50 characters",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(LocalDate.now().plusDays(1))
                .amount(new BigDecimal("10.00"))
                .build(),
            "Purchase date must be in the past or present",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(null)
                .amount(new BigDecimal("10.00"))
                .build(),
            "Purchase date is required",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("0.00"))
                .build(),
            "Amount must be greater than 0",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(LocalDate.now())
                .amount(null)
                .build(),
            "Amount is required",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("123456789012345.00"))
                .build(),
            "Amount must have up to 14 digits and 2 decimals",
            1),
        Arguments.of(
            CreatePurchaseCommand.builder()
                .description("Valid Description")
                .purchaseDate(LocalDate.now())
                .amount(new BigDecimal("10.001"))
                .build(),
            "Amount must have up to 14 digits and 2 decimals",
            1));
  }
}
