package com.hnvas.wexchagellenge.application.validation;

import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationHandlerTest {

  private ValidationHandler<Object> subject;

  @BeforeEach
  void setUp() {
    this.subject = new ValidationHandler<>();
  }

  @ParameterizedTest
  @MethodSource("givenObjects")
  void testObjetValidation(
      Object object, boolean isValid, int violationsCount) {
    boolean result = subject.isValid(object);

    assertEquals(isValid, result);
    assertEquals(violationsCount, subject.violations().size());
  }

  private static Stream<Arguments> givenObjects() {
    return Stream.of(
        Arguments.of(givenValidCreatePurchaseCommand(), true, 0),
        Arguments.of(givenInvalidPurchaseCommand(), false, 2)
    );
  }

  private static CreatePurchaseCommand givenValidCreatePurchaseCommand() {
    return CreatePurchaseCommand.builder()
        .description("This description with less than fifty chars")
        .purchaseDate(LocalDate.now())
        .amount(BigDecimal.TEN)
        .build();
  }

  private static CreatePurchaseCommand givenInvalidPurchaseCommand() {
    return CreatePurchaseCommand.builder()
        .description("")
        .purchaseDate(LocalDate.now())
        .amount(null)
        .build();
  }
}