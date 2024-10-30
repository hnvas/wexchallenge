package com.hnvas.wexchagellenge.infrastructure.persistence.exchange;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRecordRepository
    extends CrudRepository<ExchangeRateRecord, ExchangeRateCompositeKey> {

  List<ExchangeRateRecord> findByCountryAndRecordDateIsBetween(
      String country, LocalDate recordDateFrom, LocalDate recordDateTo);

  List<ExchangeRateRecord> findByCurrencyAndRecordDateIsBetween(
      String currency, LocalDate recordDateFrom, LocalDate recordDateTo);

  List<ExchangeRateRecord> findByCountryAndCurrencyAndRecordDateIsBetween(
      String country, String currency, LocalDate recordDateFrom, LocalDate recordDateTo);
}
