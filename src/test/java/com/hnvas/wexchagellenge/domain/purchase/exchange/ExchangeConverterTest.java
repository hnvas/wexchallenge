package com.hnvas.wexchagellenge.domain.purchase.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeConverter;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.purchase.exchange.LocalizedAmount;
import org.junit.jupiter.api.Test;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

class ExchangeConverterTest {

  private static final String ARGENTINA = "Argentina";
  private static final String BRAZIL = "Brazil";
  private static final String MEXICO = "Mexico";
  private static final String PESO = "Peso";
  private static final String REAL = "Real";
  private static final BigDecimal AMOUNT = new BigDecimal("100.00");
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2024, 10, 5);
  private static final LocalDate EXCHANGE_DATE = LocalDate.of(2024, 9, 30);

  public static List<ExchangeRate> givenAListOfExchangeRates() {
    return Arrays.asList(
        ExchangeRate.of(ARGENTINA, PESO, new BigDecimal("876.0"), LocalDate.parse("2024-03-31")),
        ExchangeRate.of(ARGENTINA, PESO, new BigDecimal("929.5"), LocalDate.parse("2024-06-30")),
        ExchangeRate.of(ARGENTINA, PESO, new BigDecimal("989.5"), LocalDate.parse("2024-09-30")),
        ExchangeRate.of(BRAZIL, REAL, new BigDecimal("5.015"), LocalDate.parse("2024-03-31")),
        ExchangeRate.of(BRAZIL, REAL, new BigDecimal("5.5"), LocalDate.parse("2024-06-30")),
        ExchangeRate.of(BRAZIL, REAL, new BigDecimal("5.434"), LocalDate.parse("2024-09-30")),
        ExchangeRate.of(MEXICO, PESO, new BigDecimal("16.558"), LocalDate.parse("2024-03-31")),
        ExchangeRate.of(MEXICO, PESO, new BigDecimal("18.566"), LocalDate.parse("2024-03-31")),
        ExchangeRate.of(MEXICO, PESO, new BigDecimal("18.296"), LocalDate.parse("2024-06-30")),
        ExchangeRate.of(MEXICO, PESO, new BigDecimal("19.655"), LocalDate.parse("2024-09-30")));
  }

  @Test
  void testConvert() {
    // Arrange
    List<ExchangeRate> exchangeRates = givenAListOfExchangeRates();

    Purchase purchase = Purchase.of(1L, "Test Purchase", PURCHASE_DATE, AMOUNT);

    // Act
    List<LocalizedAmount> localizedAmounts = ExchangeConverter.convert(exchangeRates, purchase);

    // Assert
    assertThat(localizedAmounts)
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(
            List.of(
                LocalizedAmount.of(
                    ExchangeRate.of(ARGENTINA, PESO, new BigDecimal("989.5"), EXCHANGE_DATE),
                    purchase),
                LocalizedAmount.of(
                    ExchangeRate.of(MEXICO, PESO, new BigDecimal("19.655"), EXCHANGE_DATE),
                    purchase),
                LocalizedAmount.of(
                    ExchangeRate.of(BRAZIL, REAL, new BigDecimal("5.434"), EXCHANGE_DATE),
                    purchase)));
  }
}
