package com.hnvas.wexchagellenge.infrastructure.persistence.exchange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.hnvas.wexchagellenge.configuration.annotation.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class ExchangeRateRecordRepositoryTest {

  private static final String COUNTRY = "Argentina";
  private static final String CURRENCY = "Peso";
  private static final LocalDate FROM_DATE = LocalDate.of(2024, 1, 1);
  private static final LocalDate TO_DATE = LocalDate.of(2024, 12, 31);
  private static final BigDecimal EXCHANGE_RATE = BigDecimal.TWO;

  @Autowired private ExchangeRateRecordRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    var exchangeRateRecord =
        new ExchangeRateRecord(COUNTRY, CURRENCY, EXCHANGE_RATE, LocalDate.now());
    repository.save(exchangeRateRecord);
  }

  @Test
  void findByCountryAndRecordDateIsBetween() {
    // Act
    List<ExchangeRateRecord> results =
        repository.findByCountryAndRecordDateIsBetween(COUNTRY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(results);
    assertFalse(results.isEmpty());
    assertEquals(1, results.size());
    assertEquals(COUNTRY, results.getFirst().getCountry());
  }

  @Test
  void findByCurrencyAndRecordDateIsBetween() {
    // Act
    List<ExchangeRateRecord> results =
        repository.findByCurrencyAndRecordDateIsBetween(CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(results);
    assertFalse(results.isEmpty());
    assertEquals(1, results.size());
    assertEquals(CURRENCY, results.getFirst().getCurrency());
  }

  @Test
  void findByCountryAndCurrencyAndRecordDateIsBetween() {
    // Act
    List<ExchangeRateRecord> results =
        repository.findByCountryAndCurrencyAndRecordDateIsBetween(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(results);
    assertFalse(results.isEmpty());
    assertEquals(1, results.size());
    assertEquals(COUNTRY, results.getFirst().getCountry());
    assertEquals(CURRENCY, results.getFirst().getCurrency());
  }
}
