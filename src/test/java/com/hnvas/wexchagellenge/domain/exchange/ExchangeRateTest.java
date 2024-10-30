package com.hnvas.wexchagellenge.domain.exchange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ExchangeRateTest {

  private static final String ARGENTINA = "Argentina";
  private static final String PESO = "Peso";
  private static final BigDecimal EXCHANGE_RATE = new BigDecimal("989.5");
  private static final LocalDate RECORD_DATE = LocalDate.of(2024, 9, 30);

  @Test
  void testExchangeRateCreation() {
    // Act
    ExchangeRate exchangeRate = ExchangeRate.of(ARGENTINA, PESO, EXCHANGE_RATE, RECORD_DATE);

    // Assert
    assertNotNull(exchangeRate);
    assertEquals(ARGENTINA, exchangeRate.country());
    assertEquals(PESO, exchangeRate.currency());
    assertEquals(EXCHANGE_RATE, exchangeRate.exchangeRate());
    assertEquals(RECORD_DATE, exchangeRate.recordDate());
  }
}
