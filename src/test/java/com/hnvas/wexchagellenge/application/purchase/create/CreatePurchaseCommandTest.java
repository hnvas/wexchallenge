package com.hnvas.wexchagellenge.application.purchase.create;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  void testInvalidDescription() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Description cannot be empty", violations.iterator().next().getMessage());
  }

  @Test
  void testNullDescription() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description(null)
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(2, violations.size());
    assertEquals("Description is required", violations.iterator().next().getMessage());
  }

  @Test
  void testDescriptionExceedsMaxLength() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("A".repeat(51))
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Description must be between 20 and 50 characters", violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidPurchaseDate() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now().plusDays(1))
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Purchase date must be in the past or present", violations.iterator().next().getMessage());
  }

  @Test
  void testNullPurchaseDate() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(null)
            .amount(new BigDecimal("10.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Purchase date is required", violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidAmount() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("0.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Amount must be greater than 0", violations.iterator().next().getMessage());
  }

  @Test
  void testNullAmount() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(null)
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Amount is required", violations.iterator().next().getMessage());
  }

  @Test
  void testAmountExceedsIntegerDigits() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("123456789012345.00"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Amount must have up to 14 digits and 2 decimals", violations.iterator().next().getMessage());
  }

  @Test
  void testAmountExceedsDecimalPlaces() {
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.001"))
            .build();

    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(1, violations.size());
    assertEquals("Amount must have up to 14 digits and 2 decimals", violations.iterator().next().getMessage());
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
}