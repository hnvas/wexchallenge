package com.hnvas.wexchagellenge.infrastructure.persistence.exchange;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExchangeRateCompositeKey {
  private String country;
  private String currency;
  private LocalDate recordDate;
}
