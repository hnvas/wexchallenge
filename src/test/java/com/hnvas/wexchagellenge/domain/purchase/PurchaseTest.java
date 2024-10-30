package com.hnvas.wexchagellenge.domain.purchase;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PurchaseTest {

  private static final Long ID = 1L;
  private static final String DESCRIPTION = "Test Purchase";
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2024, 10, 5);
  private static final BigDecimal AMOUNT = new BigDecimal("58.20");

  @Test
  void testPurchaseCreation() {
    // Act
    Purchase purchase = Purchase.of(ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);

    // Assert
    assertNotNull(purchase);
    assertEquals(ID, purchase.id());
    assertEquals(DESCRIPTION, purchase.description());
    assertEquals(PURCHASE_DATE, purchase.purchaseDate());
    assertEquals(AMOUNT, purchase.amount());
  }
}
