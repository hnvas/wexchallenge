package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model;

import java.util.List;

import lombok.Builder;

@Builder
public record ExchangeRatesResponse(List<ExchangeRateRecord> data, MetaData meta, Links links) {

  public record MetaData(int count, int totalCount, int totalPages) {}

  public record Links(String self, String first, String prev, String next, String last) {}
}
