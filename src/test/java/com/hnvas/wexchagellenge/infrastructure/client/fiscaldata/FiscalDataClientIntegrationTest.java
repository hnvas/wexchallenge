package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRateRecord;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesRequest;
import com.hnvas.wexchagellenge.infrastructure.client.fiscaldata.model.ExchangeRatesResponse;
import lombok.SneakyThrows;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MockServerContainer;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class FiscalDataClientIntegrationTest {

  private static final String COUNTRY = "Canada";
  private static final String CURRENCY = "Dollar";
  private static final String EXCHANGE_RATES_ENDPOINT =
      "/v1/accounting/od/rates_of_exchange?fields=country,currency,exchange_rate,record_date&filter=country:eq:Canada,record_date:gte:2024-01-01";

  @Value("classpath:__files/fiscaldata_api/GET_exchange_rates_success.json")
  private Resource exchangeRatesSuccessResponse;

  @Autowired
  private FiscalDataClient fiscalDataClient;

  @Autowired
  protected TestRestTemplate testRestTemplate;

  @Autowired
  private MockServerContainer mockServer;

  private MockServerClient mockServerClient;

  @BeforeEach
  void setUp() {
    mockServerClient = new MockServerClient(
        mockServer.getHost(),
        mockServer.getServerPort()
    );
  }

  @Test
  void testGetExchangeRates() {
    givenApiResponseOk(
        URI.create(EXCHANGE_RATES_ENDPOINT), exchangeRatesSuccessResponse, HttpMethod.GET);

    var response = fiscalDataClient.getExchangeRates(new ExchangeRatesRequest("Canada", null,  LocalDate.parse("2024-01-01")));

    assertThat(response).isNotNull()
        .isInstanceOf(ExchangeRatesResponse.class)
        .extracting("data", as(InstanceOfAssertFactories.LIST))
        .hasSize(3)
        .containsAll(
            List.of(
                exchangeRateRecord(1.355, LocalDate.parse("2024-03-31")),
                exchangeRateRecord(1.37, LocalDate.parse("2024-06-30")),
                exchangeRateRecord(1.352, LocalDate.parse("2024-09-30"))
            )
        );
  }

  private static @NotNull ExchangeRateRecord exchangeRateRecord(double val, LocalDate recordDate) {
    return new ExchangeRateRecord(COUNTRY, CURRENCY, BigDecimal.valueOf(val), recordDate);
  }

  @SneakyThrows
  public void givenApiResponseOk(
      URI endpoint, Resource successResponse, HttpMethod method) {
    mockServerClient.when(
        request()
            .withMethod(method.name())
            .withPath(endpoint.getPath())
    ).respond(
      response().withStatusCode(200)
        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .withBody(json(successResponse.getContentAsString(StandardCharsets.UTF_8)))
    );

  }
}