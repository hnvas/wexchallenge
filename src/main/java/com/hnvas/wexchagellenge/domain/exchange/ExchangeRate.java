package com.hnvas.wexchagellenge.domain.exchange;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ExchangeRate {

  private String country;
  private String currency;
  private BigDecimal exchangeRate;
  private LocalDate recordDate;

  private ExchangeRate(
      String country, String currency, BigDecimal exchangeRate, LocalDate recordDate) {
    this.country = country;
    this.currency = currency;
    this.exchangeRate = exchangeRate;
    this.recordDate = recordDate;
  }

  public static ExchangeRate of(
      String country, String currency, BigDecimal exchangeRate, LocalDate recordDate) {
    return new ExchangeRate(country, currency, exchangeRate, recordDate);
  }

  public String country() {
    return country;
  }

  public String currency() {
    return currency;
  }

  public BigDecimal exchangeRate() {
    return exchangeRate;
  }

  public LocalDate recordDate() {
    return recordDate;
  }
}
