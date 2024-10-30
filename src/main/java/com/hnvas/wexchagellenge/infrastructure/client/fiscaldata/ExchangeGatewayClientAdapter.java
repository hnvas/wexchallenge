package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.exchange.ExchangeRateGateway;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExchangeGatewayClientAdapter implements ExchangeRateGateway {

  private FiscalDataClient fiscalDataClient;

  @Override
  public List<ExchangeRate> findExchangeRatesByCountry(String country, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient.getExchangeRatesByCountry(country, recordDateFrom, recordDateTo).data().stream().map(ExchangeRateRecord::toModel).toList();
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCurrency(String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient.getExchangeRatesByCurrency(currency, recordDateFrom, recordDateTo).data().stream().map(ExchangeRateRecord::toModel).toList();
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCountryAndCurrency(String country, String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient.getExchangeRatesByCountryAndCurrency(country, currency, recordDateFrom, recordDateTo).data().stream().map(ExchangeRateRecord::toModel).toList();
  }
}
