package com.hnvas.wexchagellenge.infrastructure.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesOutput;
import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;

@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/exchange_rate_fixtures.sql"})
@Sql(
    scripts = {"classpath:db/cleanup.sql"},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExchangeControllerTest {

  private static final String BASE_URL = "/purchases/{id}/exchanges";
  private static final String PARAM_COUNTRY = "country";
  private static final String PARAM_CURRENCY = "currency";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void getExchanges_ShouldReturnOk() throws Exception {
    // Arrange
    Long purchaseId = 1L;
    String country = "Brazil";
    String currency = "Real";

    GetExchangesOutput output =
        new GetExchangesOutput(
            purchaseId,
            "Some description",
            LocalDate.parse("2020-07-01"),
            BigDecimal.valueOf(324.15),
            List.of(
                new GetExchangesOutput.Conversion(
                    country,
                    currency,
                    LocalDate.parse("2020-06-30"),
                    BigDecimal.valueOf(5.500),
                    BigDecimal.valueOf(1782.83))));

    // Act & Assert
    mockMvc
        .perform(
            get(BASE_URL, purchaseId)
                .param(PARAM_COUNTRY, country)
                .param(PARAM_CURRENCY, currency)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(output)));
  }

  @Test
  void getExchanges_ShouldReturnNotFound_WhenPurchaseNotFound() throws Exception {
    // Arrange
    Long purchaseId = 999L;
    String country = "Chile";
    String currency = "Peso";

    // Act & Assert
    mockMvc
        .perform(
            get(BASE_URL, purchaseId)
                .param(PARAM_COUNTRY, country)
                .param(PARAM_CURRENCY, currency)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getExchanges_ShouldReturnBadRequest_WhenCountryAndCurrencyIsNull() throws Exception {
    // Arrange
    Long purchaseId = 1L;

    // Act & Assert
    mockMvc
        .perform(
            get(BASE_URL, purchaseId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
