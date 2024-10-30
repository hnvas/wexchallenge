package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import com.hnvas.wexchagellenge.application.exception.ResourceNotFoundException;
import com.hnvas.wexchagellenge.application.exception.ValidationException;
import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRateGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetExchangesCommandHandlerTest {

  private static final Long PURCHASE_ID = 1L;
  private static final String COUNTRY_BR = "Brazil";
  private static final String CURRENCY_REAL = "Real";
  private static final String PURCHASE_DESCRIPTION = "Test Purchase";
  private static final LocalDate PURCHASE_DATE = LocalDate.now();
  private static final BigDecimal PURCHASE_AMOUNT = new BigDecimal("100.00");
  private static final BigDecimal EXCHANGE_RATE_USD = new BigDecimal("5.1");

  @Mock private PurchaseGateway purchaseGateway;
  @Mock private ExchangeRateGateway exchangeRateGateway;
  @Mock private ValidationHandler<GetExchangesCommand> validationHandler;
  @InjectMocks private GetExchangesCommandHandler handler;

  @Test
  void testHandleValidCommand() {
    GetExchangesCommand command =
        GetExchangesCommand.builder().purchaseId(PURCHASE_ID).country(COUNTRY_BR).build();

    Purchase purchase = Purchase.of(PURCHASE_ID, PURCHASE_DESCRIPTION, PURCHASE_DATE, PURCHASE_AMOUNT);
    ExchangeRate exchangeRate =
        ExchangeRate.of(COUNTRY_BR, CURRENCY_REAL, EXCHANGE_RATE_USD, PURCHASE_DATE);

    when(validationHandler.isValid(command)).thenReturn(true);
    when(purchaseGateway.findById(PURCHASE_ID)).thenReturn(Optional.of(purchase));
    when(exchangeRateGateway.findExchangeRatesByCountry(any(), any(), any()))
        .thenReturn(List.of(exchangeRate));

    GetExchangesOutput output = handler.handle(command);

    assertEquals(purchase.id(), output.id());
    assertEquals(1, output.conversions().size());
  }

  @Test
  void testHandleInvalidCommand() {
    GetExchangesCommand command =
        GetExchangesCommand.builder().purchaseId(PURCHASE_ID).country(COUNTRY_BR).build();

    when(validationHandler.isValid(command)).thenReturn(false);
    when(validationHandler.violations()).thenReturn(Set.of());

    assertThrows(ValidationException.class, () -> handler.handle(command));
  }

  @Test
  void testHandlePurchaseNotFound() {
    GetExchangesCommand command =
        GetExchangesCommand.builder().purchaseId(PURCHASE_ID).country(COUNTRY_BR).build();

    when(validationHandler.isValid(command)).thenReturn(true);
    when(purchaseGateway.findById(PURCHASE_ID)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> handler.handle(command));
  }

  @Test
  void testHandleNoExchangeRates() {
    GetExchangesCommand command =
        GetExchangesCommand.builder().purchaseId(PURCHASE_ID).country(COUNTRY_BR).build();

    Purchase purchase = Purchase.of(PURCHASE_ID, PURCHASE_DESCRIPTION, PURCHASE_DATE, PURCHASE_AMOUNT);

    when(validationHandler.isValid(command)).thenReturn(true);
    when(purchaseGateway.findById(PURCHASE_ID)).thenReturn(Optional.of(purchase));
    when(exchangeRateGateway.findExchangeRatesByCountry(any(), any(), any())).thenReturn(List.of());

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}