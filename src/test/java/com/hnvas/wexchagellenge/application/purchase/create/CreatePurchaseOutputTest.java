package com.hnvas.wexchagellenge.application.purchase.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

class CreatePurchaseOutputTest {

  @Test
  void testFromModel() {
    // Arrange
    Purchase purchase =
        Purchase.of(1L, "Valid Description", LocalDate.now(), new BigDecimal("10.00"));

    // Act
    CreatePurchaseOutput output = CreatePurchaseOutput.fromModel(purchase);

    // Assert
    assertNotNull(output);
    assertEquals(purchase.id(), output.id());
  }
}
