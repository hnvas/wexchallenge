package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRateRecord;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;

@ExtendWith(MockitoExtension.class)
class ExchangeGatewayClientAdapterTest {

  private static final String COUNTRY = "Argentina";
  private static final String CURRENCY = "Peso";
  private static final LocalDate FROM_DATE = LocalDate.of(2024, 1, 1);
  private static final LocalDate TO_DATE = LocalDate.of(2024, 12, 31);
  private static final BigDecimal EXCHANGE_RATE = BigDecimal.TWO;

  @Mock private FiscalDataClient fiscalDataClient;

  @InjectMocks private ExchangeGatewayClientAdapter exchangeGatewayClientAdapter;

  @Test
  void testFindExchangeRatesByCountry() {
    // Arrange
    List<ExchangeRateRecord> records =
        List.of(new ExchangeRateRecord(COUNTRY, CURRENCY, EXCHANGE_RATE, LocalDate.now()));
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
    List<ExchangeRateRecord> records =
        List.of(new ExchangeRateRecord(COUNTRY, CURRENCY, EXCHANGE_RATE, LocalDate.now()));
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
    List<ExchangeRateRecord> records =
        List.of(new ExchangeRateRecord(COUNTRY, CURRENCY, EXCHANGE_RATE, LocalDate.now()));
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
