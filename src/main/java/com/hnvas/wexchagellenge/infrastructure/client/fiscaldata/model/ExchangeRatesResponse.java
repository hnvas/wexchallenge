package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ExchangeRatesResponse(
    List<CurrencyRecord> data,
    MetaData meta,
    Links links
) {

  public record MetaData(
      int count,
      int totalCount,
      int totalPages
  ) {}

  public record Links(
      String self,
      String first,
      String prev,
      String next,
      String last
  ) {}
}
