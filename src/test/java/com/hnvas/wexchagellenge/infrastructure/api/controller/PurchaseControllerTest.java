package com.hnvas.wexchagellenge.infrastructure.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseCommand;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseOutput;
import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PurchaseControllerTest {

  private static final String BASE_URL = "/purchases";
  private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void createPurchase_ShouldReturnCreated() throws Exception {
    // Arrange
    CreatePurchaseCommand command = CreatePurchaseCommand.builder()
        .description("Valid Description")
        .purchaseDate(LocalDate.now())
        .amount(new BigDecimal("10.00"))
        .build();

    CreatePurchaseOutput output = new CreatePurchaseOutput(1L);

    // Act & Assert
    mockMvc.perform(post(BASE_URL)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(command)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE))
        .andExpect(content().json(objectMapper.writeValueAsString(output)));
  }

  @Test
  void createPurchase_ShouldReturnUnprocessableEntity_WhenInvalid() throws Exception {
    // Arrange
    CreatePurchaseCommand command = CreatePurchaseCommand.builder()
        .description("")
        .purchaseDate(LocalDate.now().plusDays(1))
        .amount(BigDecimal.ZERO)
        .build();

    // Act & Assert
    mockMvc.perform(post(BASE_URL)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(command)))
        .andExpect(status().isUnprocessableEntity());
  }
}