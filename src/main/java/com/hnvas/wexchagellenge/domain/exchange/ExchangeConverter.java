package com.hnvas.wexchagellenge.domain.exchange;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExchangeConverter {

  public static List<LocalizedAmount> convert(List<ExchangeRate> exchangeRates, Purchase purchase) {
    return exchangeRates.stream()
        .collect(
            Collectors.groupingBy(
                exchangeRate -> exchangeRate.country() + "-" + exchangeRate.currency(),
                Collectors.maxBy(Comparator.comparing(ExchangeRate::recordDate))))
        .values()
        .stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(exchangeRate -> LocalizedAmount.of(exchangeRate, purchase))
        .toList();
  }
}
