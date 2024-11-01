package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRate;
import com.hnvas.wexchagellenge.domain.purchase.exchange.LocalizedAmount;

class GetExchangesOutputTest {

  private static final Long PURCHASE_ID = 1L;
  private static final String PURCHASE_DESCRIPTION = "Test Purchase";
  private static final LocalDate PURCHASE_DATE = LocalDate.now();
  private static final BigDecimal PURCHASE_AMOUNT = new BigDecimal("100.00");
  private static final String COUNTRY_FR = "France";
  private static final String CURRENCY_FR = "Euro";
  private static final BigDecimal EXCHANGE_RATE_USD = new BigDecimal("1.2");
  private static final String COUNTRY_UK = "England";
  private static final String CURRENCY_UK = "Pound";
  private static final BigDecimal EXCHANGE_RATE_GBP = new BigDecimal("0.8");

  @Test
  void testGetExchangesOutputCreation() {
    Purchase purchase =
        Purchase.of(PURCHASE_ID, PURCHASE_DESCRIPTION, PURCHASE_DATE, PURCHASE_AMOUNT);
    ExchangeRate exchangeRate =
        ExchangeRate.of(COUNTRY_FR, CURRENCY_FR, EXCHANGE_RATE_USD, PURCHASE_DATE);
    LocalizedAmount localizedAmount = LocalizedAmount.of(exchangeRate, purchase);

    GetExchangesOutput output = GetExchangesOutput.of(purchase, List.of(localizedAmount));

    assertEquals(purchase.id(), output.id());
    assertEquals(purchase.description(), output.description());
    assertEquals(purchase.purchaseDate(), output.purchaseDate());
    assertEquals(purchase.amount(), output.amount());
    assertEquals(1, output.conversions().size());

    GetExchangesOutput.Conversion conversion = output.conversions().getFirst();
    assertEquals(exchangeRate.country(), conversion.country());
    assertEquals(exchangeRate.currency(), conversion.currency());
    assertEquals(exchangeRate.recordDate(), conversion.recordDate());
    assertEquals(exchangeRate.exchangeRate(), conversion.exchangeRate());
    assertEquals(localizedAmount.convertedAmount(), conversion.convertedAmount());
  }

  @Test
  void testGetExchangesOutputWithMultipleConversions() {
    Purchase purchase =
        Purchase.of(PURCHASE_ID, PURCHASE_DESCRIPTION, PURCHASE_DATE, PURCHASE_AMOUNT);
    ExchangeRate exchangeRate1 =
        ExchangeRate.of(COUNTRY_FR, CURRENCY_FR, EXCHANGE_RATE_USD, PURCHASE_DATE);
    ExchangeRate exchangeRate2 =
        ExchangeRate.of(COUNTRY_UK, CURRENCY_UK, EXCHANGE_RATE_GBP, PURCHASE_DATE);
    LocalizedAmount localizedAmount1 = LocalizedAmount.of(exchangeRate1, purchase);
    LocalizedAmount localizedAmount2 = LocalizedAmount.of(exchangeRate2, purchase);

    GetExchangesOutput output =
        GetExchangesOutput.of(purchase, List.of(localizedAmount1, localizedAmount2));

    assertEquals(2, output.conversions().size());

    GetExchangesOutput.Conversion conversion1 = output.conversions().getFirst();
    assertEquals(exchangeRate1.country(), conversion1.country());
    assertEquals(exchangeRate1.currency(), conversion1.currency());
    assertEquals(exchangeRate1.recordDate(), conversion1.recordDate());
    assertEquals(exchangeRate1.exchangeRate(), conversion1.exchangeRate());
    assertEquals(localizedAmount1.convertedAmount(), conversion1.convertedAmount());

    GetExchangesOutput.Conversion conversion2 = output.conversions().get(1);
    assertEquals(exchangeRate2.country(), conversion2.country());
    assertEquals(exchangeRate2.currency(), conversion2.currency());
    assertEquals(exchangeRate2.recordDate(), conversion2.recordDate());
    assertEquals(exchangeRate2.exchangeRate(), conversion2.exchangeRate());
    assertEquals(localizedAmount2.convertedAmount(), conversion2.convertedAmount());
  }
}
