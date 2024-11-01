package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.testcontainers.containers.MockServerContainer;

import com.hnvas.wexchagellenge.configuration.annotation.IntegrationTest;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;
import com.hnvas.wexchagellenge.infrastructure.persistence.exchange.ExchangeRateRecord;

import lombok.SneakyThrows;

@IntegrationTest
class FiscalDataClientIntegrationTest {

  private static final String EXCHANGE_RATES_BY_COUNTRY_ENDPOINT =
      "/v1/accounting/od/rates_of_exchange?fields=country,currency,exchange_rate,record_date&filter=country:eq:Canada,record_date:gte:2024-01-01,record_date:lte:2024-12-31";
  private static final String EXCHANGE_RATES_BY_CURRENCY_ENDPOINT =
      "/v1/accounting/od/rates_of_exchange?fields=country,currency,exchange_rate,record_date&filter=currency:eq:Yen,record_date:gte:2024-01-01,record_date:lte:2024-12-31";
  private static final String EXCHANGE_RATES_BY_COUNTRY_AND_CURRENCY_ENDPOINT =
      "/v1/accounting/od/rates_of_exchange?fields=country,currency,exchange_rate,record_date&filter=country_currency_desc:eq:Chile-Peso,record_date:gte:2024-01-01,record_date:lte:2024-12-31";
  @Autowired protected TestRestTemplate testRestTemplate;

  @Value("classpath:__files/fiscaldata_api/GET_exchange_rates_by_country_success.json")
  private Resource exchangeRatesByCountrySuccessResponse;

  @Value("classpath:__files/fiscaldata_api/GET_exchange_rates_by_currency_success.json")
  private Resource exchangeRatesByCurrencySuccessResponse;

  @Value("classpath:__files/fiscaldata_api/GET_exchange_rates_by_country_and_currency_success.json")
  private Resource exchangeRatesByCountryAndCurrencySuccessResponse;

  @Autowired private FiscalDataClient fiscalDataClient;
  @Autowired private MockServerContainer mockServer;

  private MockServerClient mockServerClient;

  private static @NotNull ExchangeRateRecord exchangeRateRecord(
      String country, String currency, double val, LocalDate recordDate) {
    return new ExchangeRateRecord(country, currency, BigDecimal.valueOf(val), recordDate);
  }

  @BeforeEach
  void setUp() {
    mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());
  }

  @AfterEach
  void tearDown() {
    mockServerClient.reset();
  }

  @Test
  void testGetExchangeRatesByCountry() {
    givenApiResponseOk(
        URI.create(EXCHANGE_RATES_BY_COUNTRY_ENDPOINT),
        exchangeRatesByCountrySuccessResponse,
        HttpMethod.GET);

    var response =
        fiscalDataClient.getExchangeRatesByCountry(
            "Canada", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ExchangeRatesResponse.class)
        .extracting("data", as(InstanceOfAssertFactories.LIST))
        .hasSize(3)
        .containsAll(
            List.of(
                exchangeRateRecord("Canada", "Dollar", 1.355, LocalDate.parse("2024-03-31")),
                exchangeRateRecord("Canada", "Dollar", 1.37, LocalDate.parse("2024-06-30")),
                exchangeRateRecord("Canada", "Dollar", 1.352, LocalDate.parse("2024-09-30"))));
  }

  @Test
  void testGetExchangeRatesByCurrency() {
    givenApiResponseOk(
        URI.create(EXCHANGE_RATES_BY_CURRENCY_ENDPOINT),
        exchangeRatesByCurrencySuccessResponse,
        HttpMethod.GET);

    var response =
        fiscalDataClient.getExchangeRatesByCurrency(
            "Yen", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ExchangeRatesResponse.class)
        .extracting("data", as(InstanceOfAssertFactories.LIST))
        .hasSize(3)
        .containsAll(
            List.of(
                exchangeRateRecord("Japan", "Yen", 151.34, LocalDate.parse("2024-03-31")),
                exchangeRateRecord("Japan", "Yen", 160.63, LocalDate.parse("2024-06-30")),
                exchangeRateRecord("Japan", "Yen", 140.04, LocalDate.parse("2024-09-30"))));
  }

  @Test
  void testGetExchangeRatesByCountryAndCurrency() {
    givenApiResponseOk(
        URI.create(EXCHANGE_RATES_BY_COUNTRY_AND_CURRENCY_ENDPOINT),
        exchangeRatesByCountryAndCurrencySuccessResponse,
        HttpMethod.GET);

    var response =
        fiscalDataClient.getExchangeRatesByCountryAndCurrency(
            "Chile", "Peso", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ExchangeRatesResponse.class)
        .extracting("data", as(InstanceOfAssertFactories.LIST))
        .hasSize(2)
        .containsAll(
            List.of(
                exchangeRateRecord("Chile", "Peso", 978.97, LocalDate.parse("2024-03-31")),
                exchangeRateRecord("Chile", "Peso", 954.5, LocalDate.parse("2024-06-30"))));
  }

  @SneakyThrows
  public void givenApiResponseOk(URI endpoint, Resource successResponse, HttpMethod method) {
    mockServerClient
        .when(request().withMethod(method.name()).withPath(endpoint.getPath()))
        .respond(
            response()
                .withStatusCode(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(json(successResponse.getContentAsString(StandardCharsets.UTF_8))));
  }
}
