package com.hnvas.wexchagellenge.infrastructure.persistence.exchange;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExchangeRateCompositeKey {
  private String country;
  private String currency;
  private LocalDate recordDate;
}
