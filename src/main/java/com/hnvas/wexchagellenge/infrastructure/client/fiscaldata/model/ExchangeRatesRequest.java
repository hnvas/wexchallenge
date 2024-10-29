package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model;

import java.time.LocalDate;

public record ExchangeRatesRequest(
    String country,
    String currency,
    LocalDate recordDate
) {}
