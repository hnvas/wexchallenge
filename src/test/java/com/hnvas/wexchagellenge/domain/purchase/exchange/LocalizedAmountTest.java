package com.hnvas.wexchagellenge.domain.purchase.exchange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hnvas.wexchagellenge.configuration.annotation.UnitTest;
import org.junit.jupiter.api.Test;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

@UnitTest
class LocalizedAmountTest {

  private static final String ARGENTINA = "Argentina";
  private static final String PESO = "Peso";
  private static final BigDecimal EXCHANGE_RATE = new BigDecimal("989.5");
  private static final LocalDate RECORD_DATE = LocalDate.of(2024, 9, 30);
  private static final BigDecimal AMOUNT = new BigDecimal("58.20");
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2024, 10, 5);

  @Test
  void testLocalizedAmountCreation() {
    // Arrange
    ExchangeRate exchangeRate = ExchangeRate.of(ARGENTINA, PESO, EXCHANGE_RATE, RECORD_DATE);
    Purchase purchase = Purchase.of(1L, "Test Purchase", PURCHASE_DATE, AMOUNT);

    // Act
    LocalizedAmount localizedAmount = LocalizedAmount.of(exchangeRate, purchase);

    // Assert
    assertNotNull(localizedAmount);
    assertEquals(exchangeRate, localizedAmount.exchangeRate());
    assertEquals(
        EXCHANGE_RATE.multiply(AMOUNT).doubleValue(),
        localizedAmount.convertedAmount().doubleValue());
  }
}
