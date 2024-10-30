package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.exchange.LocalizedAmount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record GetExchangesOutput(
    Long id,
    String description,
    LocalDate purchaseDate,
    BigDecimal amount,
    List<Conversion> conversions
) {
  public record Conversion(
      String country,
      String currency,
      LocalDate recordDate,
      BigDecimal exchangeRate,
      BigDecimal convertedAmount
  ) {
  }

  public static GetExchangesOutput of(Purchase purchase, List<LocalizedAmount> amountList) {
    return new GetExchangesOutput(purchase.id(), purchase.description(), purchase.purchaseDate(),
        purchase.amount(), amountList.stream()
            .map(localizedAmount -> new Conversion(
                localizedAmount.exchangeRate().country(),
                localizedAmount.exchangeRate().currency(),
                localizedAmount.exchangeRate().recordDate(),
                localizedAmount.exchangeRate().exchangeRate(),
                localizedAmount.convertedAmount()))
            .toList());
  }
}
