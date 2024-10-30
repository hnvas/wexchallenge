package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GetExchangesCommand(
    @NotNull(message = "Purchase ID is required") Long purchaseId,
    String country,
    String currency) {

  @AssertTrue(message = "Either country or currency must be provided")
  public boolean isCountryOrCurrencyProvided() {
    return StringUtils.isNotEmpty(country) || StringUtils.isNotEmpty(currency);
  }

  public ExchangesQueryType queryType() {
    if (StringUtils.isNotEmpty(country) && StringUtils.isNotEmpty(currency)) {
      return ExchangesQueryType.COUNTRY_AND_CURRENCY;
    } else if (StringUtils.isNotEmpty(country)) {
      return ExchangesQueryType.COUNTRY;
    } else {
      return ExchangesQueryType.CURRENCY;
    }
  }
}
