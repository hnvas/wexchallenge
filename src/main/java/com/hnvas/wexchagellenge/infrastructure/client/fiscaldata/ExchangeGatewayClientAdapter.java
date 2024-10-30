package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.exchange.ExchangeRateGateway;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRateRecord;

@Component
public class ExchangeGatewayClientAdapter implements ExchangeRateGateway {

  private final FiscalDataClient fiscalDataClient;

  public ExchangeGatewayClientAdapter(FiscalDataClient fiscalDataClient) {
    this.fiscalDataClient = fiscalDataClient;
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCountry(
      String country, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient
        .getExchangeRatesByCountry(country, recordDateFrom, recordDateTo)
        .data()
        .stream()
        .map(ExchangeRateRecord::toModel)
        .toList();
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCurrency(
      String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient
        .getExchangeRatesByCurrency(currency, recordDateFrom, recordDateTo)
        .data()
        .stream()
        .map(ExchangeRateRecord::toModel)
        .toList();
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCountryAndCurrency(
      String country, String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    return fiscalDataClient
        .getExchangeRatesByCountryAndCurrency(country, currency, recordDateFrom, recordDateTo)
        .data()
        .stream()
        .map(ExchangeRateRecord::toModel)
        .toList();
  }
}