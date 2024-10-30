package com.hnvas.wexchagellenge.application.exception;

public class NoExchangeRateToConvertException extends RuntimeException {

  private static final String NO_EXCHANGE_RATE_MESSAGE = "No exchange rate found to convert";

  private NoExchangeRateToConvertException() {
    super(NO_EXCHANGE_RATE_MESSAGE);
  }

  public static NoExchangeRateToConvertException noExchangeRateToConvert() {
    return new NoExchangeRateToConvertException();
  }
}
