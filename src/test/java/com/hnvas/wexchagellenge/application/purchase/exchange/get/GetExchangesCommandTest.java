package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetExchangesCommandTest {

  private ValidationHandler<Object> validator;

  @BeforeEach
  void setUp() {
    this.validator = new ValidationHandler<>();
  }

  @Test
  void testValidGetExchangesCommand() {
    GetExchangesCommand command = GetExchangesCommand.builder()
        .country("England")
        .currency("Pound")
        .purchaseId(10L)
        .build();

    validator.isValid(command);

    assertEquals(0, validator.violations().size());
  }

  @ParameterizedTest
  @MethodSource("invalidCommandProvider")
  void testInvalidGetExchangesCommand(GetExchangesCommand command, String expectedMessage, int expectedViolations) {
    validator.isValid(command);

    var violations = validator.violations();
    assertEquals(expectedViolations, violations.size());
    assertEquals(expectedMessage, violations.iterator().next().getMessage());
  }

  private static Stream<Arguments> invalidCommandProvider() {
    return Stream.of(
        Arguments.of(
            GetExchangesCommand.builder()
                .purchaseId(1L)
                .build(),
            "Either country or currency must be provided", 1),
        Arguments.of(
            GetExchangesCommand.builder()
                .country("Australia")
                .build(),
            "Purchase ID is required", 1),
        Arguments.of(
            GetExchangesCommand.builder()
                .currency("Dollar")
                .build(),
            "Purchase ID is required", 1),
        Arguments.of(
            GetExchangesCommand.builder()
                .country("")
                .currency("")
                .purchaseId(1L)
                .build(),
            "Either country or currency must be provided", 1),
        Arguments.of(
            GetExchangesCommand.builder()
                .country(null)
                .currency(null)
                .purchaseId(1L)
                .build(),
            "Either country or currency must be provided", 1)
    );
  }
}