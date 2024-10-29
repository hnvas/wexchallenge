package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import com.hnvas.wexchagellenge.infrastructure.client.RetryableApiClient;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesRequest;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FiscalDataClient extends RetryableApiClient {

  private static final String ENDPOINT = "/v1/accounting/od/rates_of_exchange";
  private static final String FIELDS_PARAM = "fields";
  private static final String FILTER_PARAM = "filter";
  private static final String FIELDS_VALUES = "country,currency,exchange_rate,record_date";
  private static final String FILTER_COUNTRY_VALUE = "country:eq:%s,record_date:gte:%s";
  private static final String FILTER_CURRENCY_VALUE = "country:eq:%s,record_date:gte:%s";
  private static final String FILTER_COUNTRY_CURRENCY_VALUE = "country_currency_desc:eq:%s-%s,record_date:gte:%s";

  public FiscalDataClient(RetryTemplate retryTemplate, FiscalDataRestTemplateFactory restTemplateFactory) {
    super(retryTemplate, restTemplateFactory.fiscalDataRestTemplate());
  }

  public ExchangeRatesResponse getExchangeRates(ExchangeRatesRequest request) {
    String requestUrl = buildRequestUrl(request);
    return getForObject(requestUrl, ExchangeRatesResponse.class);
  }

  private String buildRequestUrl(ExchangeRatesRequest request) {
    var uriBuilder = UriComponentsBuilder.newInstance()
        .path(ENDPOINT)
        .queryParam(FIELDS_PARAM, FIELDS_VALUES);

    if (request.country() != null && request.currency() != null) {
      String countryCurrencyFilter = FILTER_COUNTRY_CURRENCY_VALUE.formatted(request.country(), request.currency(), request.recordDate());
      uriBuilder.queryParam(FILTER_PARAM, countryCurrencyFilter);
    } else if (request.country() != null) {
      String countryFilter = FILTER_COUNTRY_VALUE.formatted(request.country(), request.recordDate());
      uriBuilder.queryParam(FILTER_PARAM, countryFilter);
    } else if (request.currency() != null) {
      String currencyFilter = FILTER_CURRENCY_VALUE.formatted(request.currency(), request.recordDate());
      uriBuilder.queryParam(FILTER_PARAM, currencyFilter);
    }

    return uriBuilder.build().toString();
  }

}
