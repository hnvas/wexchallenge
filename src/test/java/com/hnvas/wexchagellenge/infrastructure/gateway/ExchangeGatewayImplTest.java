package com.hnvas.wexchagellenge.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.FiscalDataClient;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecord;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecordRepository;

@ExtendWith(MockitoExtension.class)
class ExchangeGatewayImplTest {

  private static final String COUNTRY = "Argentina";
  private static final String CURRENCY = "Peso";
  private static final LocalDate FROM_DATE = LocalDate.of(2024, 1, 1);
  private static final LocalDate TO_DATE = LocalDate.of(2024, 12, 31);
  private static final BigDecimal EXCHANGE_RATE = BigDecimal.TWO;

  @Mock private FiscalDataClient fiscalDataClient;
  @Mock private ExchangeRateRecordRepository exchangeRateRecordRepository;

  @InjectMocks private ExchangeGatewayImpl exchangeGatewayClientAdapter;

  private List<ExchangeRateRecord> records;

  @BeforeEach
  void setUp() {
    records = List.of(new ExchangeRateRecord(COUNTRY, CURRENCY, EXCHANGE_RATE, LocalDate.now()));
  }

  @Test
  void testFindExchangeRatesByCountry() {
    // Arrange
    when(exchangeRateRecordRepository.findByCountryAndRecordDateIsBetween(
            COUNTRY, FROM_DATE, TO_DATE))
        .thenReturn(records);

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCountry(COUNTRY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }

  @Test
  void testFindExchangeRatesByCountry_FallbackToClient() {
    // Arrange
    when(exchangeRateRecordRepository.findByCountryAndRecordDateIsBetween(
            COUNTRY, FROM_DATE, TO_DATE))
        .thenReturn(Collections.emptyList());
    when(fiscalDataClient.getExchangeRatesByCountry(COUNTRY, FROM_DATE, TO_DATE))
        .thenReturn(new ExchangeRatesResponse(records, null, null));

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCountry(COUNTRY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }

  @Test
  void testFindExchangeRatesByCurrency() {
    // Arrange
    when(exchangeRateRecordRepository.findByCurrencyAndRecordDateIsBetween(
            CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(records);

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCurrency(CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }

  @Test
  void testFindExchangeRatesByCurrency_FallbackToClient() {
    // Arrange
    when(exchangeRateRecordRepository.findByCurrencyAndRecordDateIsBetween(
            CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(Collections.emptyList());
    when(fiscalDataClient.getExchangeRatesByCurrency(CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(new ExchangeRatesResponse(records, null, null));

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCurrency(CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }

  @Test
  void testFindExchangeRatesByCountryAndCurrency() {
    // Arrange
    when(exchangeRateRecordRepository.findByCountryAndCurrencyAndRecordDateIsBetween(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(records);

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCountryAndCurrency(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }

  @Test
  void testFindExchangeRatesByCountryAndCurrency_FallbackToClient() {
    // Arrange
    when(exchangeRateRecordRepository.findByCountryAndCurrencyAndRecordDateIsBetween(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(Collections.emptyList());
    when(fiscalDataClient.getExchangeRatesByCountryAndCurrency(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE))
        .thenReturn(new ExchangeRatesResponse(records, null, null));

    // Act
    List<ExchangeRate> result =
        exchangeGatewayClientAdapter.findExchangeRatesByCountryAndCurrency(
            COUNTRY, CURRENCY, FROM_DATE, TO_DATE);

    // Assert
    assertNotNull(result);
    assertEquals(records.size(), result.size());
  }
}
