package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PurchaseRecordTest {

  private static final Long ID = 1L;
  private static final String DESCRIPTION = "Test Description";
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2023, 1, 1);
  private static final BigDecimal AMOUNT = new BigDecimal("100.00");

  @Test
  void testToModel() {
    // Arrange
    PurchaseRecord purchaseRecord = new PurchaseRecord(ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);

    // Act
    Purchase purchase = purchaseRecord.toModel();

    // Assert
    assertNotNull(purchase);
    assertEquals(purchaseRecord.getId(), purchase.id());
    assertEquals(purchaseRecord.getDescription(), purchase.description());
    assertEquals(purchaseRecord.getPurchaseDate(), purchase.purchaseDate());
    assertEquals(purchaseRecord.getAmount(), purchase.amount());
  }

  @Test
  void testFromModel() {
    // Arrange
    Purchase purchase = Purchase.of(ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);

    // Act
    PurchaseRecord purchaseRecord = PurchaseRecord.fromModel(purchase);

    // Assert
    assertNotNull(purchaseRecord);
    assertEquals(purchase.id(), purchaseRecord.getId());
    assertEquals(purchase.description(), purchaseRecord.getDescription());
    assertEquals(purchase.purchaseDate(), purchaseRecord.getPurchaseDate());
    assertEquals(purchase.amount(), purchaseRecord.getAmount());
  }
}