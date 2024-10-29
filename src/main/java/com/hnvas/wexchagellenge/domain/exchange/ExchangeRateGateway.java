package com.hnvas.wexchagellenge.domain.exchange;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateGateway {

  List<ExchangeRate> findExchangeRatesByCountry(String country, LocalDate recordDateFrom, LocalDate recordDateTo);

  List<ExchangeRate> findExchangeRatesByCurrency(String currency, LocalDate recordDateFrom, LocalDate recordDateTo);

  List<ExchangeRate> findExchangeRatesByCountryAndCurrency(String country, LocalDate recordDateFrom, LocalDate recordDateTo);
}
