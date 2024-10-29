package com.hnvas.wexchagellenge.domain.exchange;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EqualsAndHashCode
public class LocalizedAmount {

  public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
  private static final int PRECISION_SCALE = 2;
  private final ExchangeRate exchangeRate;
  private final BigDecimal convertedAmount;

  private LocalizedAmount(ExchangeRate exchangeRate, BigDecimal convertedAmount) {
    this.exchangeRate = exchangeRate;
    this.convertedAmount = convertedAmount;
  }

  public static LocalizedAmount of(ExchangeRate exchangeRate, Purchase purchase) {
    return new LocalizedAmount(exchangeRate, computeConversion(exchangeRate, purchase.amount()));
  }

  private static BigDecimal computeConversion(ExchangeRate exchangeRate, BigDecimal amount) {
    return amount
        .multiply(exchangeRate.exchangeRate())
        .setScale(PRECISION_SCALE, ROUNDING_MODE);
  }

  public ExchangeRate exchangeRate() {
    return exchangeRate;
  }

  public BigDecimal convertedAmount() {
    return convertedAmount;
  }
}
