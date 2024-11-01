package com.hnvas.wexchagellenge.infrastructure.gateway;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRateGateway;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.FiscalDataClient;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecord;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecordRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExchangeGatewayImpl implements ExchangeRateGateway {

  private static final String FOUND_IN_DB_MESSAGE =
      "Exchange rates found in database, resuming with {} records";
  private static final String NOT_FOUND_IN_DB_MESSAGE =
      "Exchange rates not found in database, fetching from external service";

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
    log.info(
        "Fetching exchange rates for country: {} between dates: {} and {}",
        country,
        recordDateFrom,
        recordDateTo);

    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCountryAndRecordDateIsBetween(
            country, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      log.info(FOUND_IN_DB_MESSAGE, exchangeRateRecordList.size());
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    log.info(NOT_FOUND_IN_DB_MESSAGE);
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
    log.info(
        "Fetching exchange rates for currency: {} between dates: {} and {}",
        currency,
        recordDateFrom,
        recordDateTo);

    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCurrencyAndRecordDateIsBetween(
            currency, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      log.info(FOUND_IN_DB_MESSAGE, exchangeRateRecordList.size());
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    log.info(NOT_FOUND_IN_DB_MESSAGE);
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
    log.info(
        "Fetching exchange rates for country: {} and currency: {} between dates: {} and {}",
        country,
        currency,
        recordDateFrom,
        recordDateTo);

    List<ExchangeRateRecord> exchangeRateRecordList =
        exchangeRateRecordRepository.findByCountryAndCurrencyAndRecordDateIsBetween(
            country, currency, recordDateFrom, recordDateTo);

    if (!exchangeRateRecordList.isEmpty()) {
      log.info(FOUND_IN_DB_MESSAGE, exchangeRateRecordList.size());
      return exchangeRateRecordList.stream().map(ExchangeRateRecord::toModel).toList();
    }

    log.info(NOT_FOUND_IN_DB_MESSAGE);
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
