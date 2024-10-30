package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import com.hnvas.wexchagellenge.infrastructure.client.RetryableApiClient;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class FiscalDataClient extends RetryableApiClient {

  private static final String ENDPOINT = "/v1/accounting/od/rates_of_exchange";
  private static final String FIELDS_PARAM = "fields";
  private static final String FILTER_PARAM = "filter";
  private static final String FIELDS_VALUES = "country,currency,exchange_rate,record_date";
  private static final String FILTER_COUNTRY_VALUE = "country:eq:%s,record_date:gte:%s,record_date:lte:%s";
  private static final String FILTER_CURRENCY_VALUE = "country:eq:%s,record_date:gte:%s,record_date:lte:%s";
  private static final String FILTER_COUNTRY_CURRENCY_VALUE = "country_currency_desc:eq:%s-%s,record_date:gte:%s,record_date:lte:%s";

  public FiscalDataClient(RetryTemplate retryTemplate, FiscalDataRestTemplateFactory restTemplateFactory) {
    super(retryTemplate, restTemplateFactory.fiscalDataRestTemplate());
  }

  private UriComponentsBuilder endpointBuilder() {
    return UriComponentsBuilder.newInstance()
        .path(ENDPOINT)
        .queryParam(FIELDS_PARAM, FIELDS_VALUES);
  }

  public ExchangeRatesResponse getExchangeRatesByCountry(String country, LocalDate recordDateFrom, LocalDate recordDateTo) {
    var requestURI = endpointBuilder().queryParam(FILTER_PARAM, FILTER_COUNTRY_VALUE.formatted(country, recordDateFrom, recordDateTo)).build().toString();
    return getForObject(requestURI, ExchangeRatesResponse.class);
  }

  public ExchangeRatesResponse getExchangeRatesByCurrency(String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    var requestURI = endpointBuilder().queryParam(FILTER_PARAM, FILTER_CURRENCY_VALUE.formatted(currency, recordDateFrom, recordDateTo)).build().toString();
    return getForObject(requestURI, ExchangeRatesResponse.class);
  }

  public ExchangeRatesResponse getExchangeRatesByCountryAndCurrency(String country, String currency, LocalDate recordDateFrom, LocalDate recordDateTo) {
    var requestURI = endpointBuilder().queryParam(FILTER_PARAM, FILTER_COUNTRY_CURRENCY_VALUE.formatted(country, currency, recordDateFrom, recordDateTo)).build().toString();
    return getForObject(requestURI, ExchangeRatesResponse.class);
  }
}
