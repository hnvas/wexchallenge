package com.hnvas.wexchagellenge.infrastructure.persistence.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "exchange_rate")
@IdClass(ExchangeRateCompositeKey.class)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExchangeRateRecord {

  @Id
  @Column(name = "country", nullable = false, length = 50)
  private String country;

  @Id
  @Column(name = "currency", nullable = false, length = 50)
  private String currency;

  @JsonProperty("exchange_rate")
  @Column(name = "rate_value", nullable = false)
  private BigDecimal exchangeRate;

  @Id
  @JsonProperty("record_date")
  @Column(name = "record_date", nullable = false)
  private LocalDate recordDate;

  public static ExchangeRate toModel(ExchangeRateRecord exchangeRateRecord) {
    return ExchangeRate.of(
        exchangeRateRecord.getCountry(),
        exchangeRateRecord.getCurrency(),
        exchangeRateRecord.getExchangeRate(),
        exchangeRateRecord.getRecordDate());
  }

  public static ExchangeRateRecord fromModel(ExchangeRate exchangeRate) {
    return new ExchangeRateRecord(
        exchangeRate.country(),
        exchangeRate.currency(),
        exchangeRate.exchangeRate(),
        exchangeRate.recordDate());
  }
}