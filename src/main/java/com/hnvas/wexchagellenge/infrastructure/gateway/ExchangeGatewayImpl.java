package com.hnvas.wexchagellenge.infrastructure.gateway;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.exchange.ExchangeRateGateway;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.FiscalDataClient;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecord;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecordRepository;

@Component
public class ExchangeGatewayImpl implements ExchangeRateGateway {

  private final FiscalDataClient fiscalDataClient;
  private final ExchangeRateRecordRepository exchangeRateRecordRepository;

  public ExchangeGatewayImpl(
      FiscalDataClient fiscalDataClient,
      ExchangeRateRecordRepository exchangeRateRecordRepository) {
    this.fiscalDataClient = fiscalDataClient;
    this.exchangeRateRecordRepository = exchangeRateRecordRepository;
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCountry(
      String country, LocalDate recordDateFrom, LocalDate recordDateTo) {
    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCountryAndRecordDateIsBetween(
            country, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    return persistAsync(
        fiscalDataClient
            .getExchangeRatesByCountry(country, recordDateFrom, recordDateTo)
            .data()
            .stream()
            .map(ExchangeRateRecord::toModel)
            .toList());
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCurrency(
      String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCurrencyAndRecordDateIsBetween(
            currency, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    return persistAsync(
        fiscalDataClient
            .getExchangeRatesByCurrency(currency, recordDateFrom, recordDateTo)
            .data()
            .stream()
            .map(ExchangeRateRecord::toModel)
            .toList());
  }

  @Override
  public List<ExchangeRate> findExchangeRatesByCountryAndCurrency(
      String country, String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCountryAndCurrencyAndRecordDateIsBetween(
            country, currency, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    return persistAsync(
        fiscalDataClient
            .getExchangeRatesByCountryAndCurrency(country, currency, recordDateFrom, recordDateTo)
            .data()
            .stream()
            .map(ExchangeRateRecord::toModel)
            .toList());
  }

  private List<ExchangeRate> persistAsync(List<ExchangeRate> list) {
    runAsync(
        () ->
            exchangeRateRecordRepository.saveAll(
                list.stream().map(ExchangeRateRecord::fromModel).toList()));

    return list;
  }
}
