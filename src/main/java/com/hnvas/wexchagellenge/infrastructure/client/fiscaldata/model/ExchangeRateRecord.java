package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hnvas.wexchagellenge.domain.exchange.ExchangeRate;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateRecord(
    String country,
    String currency,
    @JsonProperty("exchange_rate") BigDecimal exchangeRate,
    @JsonProperty("record_date") LocalDate recordDate
) {

  public static ExchangeRate toModel(ExchangeRateRecord exchangeRateRecord) {
    return ExchangeRate.of(
        exchangeRateRecord.country(),
        exchangeRateRecord.currency(),
        exchangeRateRecord.exchangeRate(),
        exchangeRateRecord.recordDate()
    );
  }
}
