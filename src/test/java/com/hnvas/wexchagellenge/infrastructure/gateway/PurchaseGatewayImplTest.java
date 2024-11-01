package com.hnvas.wexchagellenge.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hnvas.wexchagellenge.configuration.annotation.UnitTest;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.infrastructure.persistence.purchase.PurchaseRecord;
import com.hnvas.wexchagellenge.infrastructure.persistence.purchase.PurchaseRecordRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PurchaseGatewayImplTest {

  private static final Long PURCHASE_ID = 1L;
  private static final String DESCRIPTION = "Test Description";
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2023, 1, 1);
  private static final BigDecimal AMOUNT = new BigDecimal("100.00");

  @Mock private PurchaseRecordRepository purchaseRepository;

  @InjectMocks private PurchaseGatewayImpl purchaseGateway;

  @Test
  void testSave() {
    // Arrange
    var purchase = Purchase.of(PURCHASE_ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);
    var purchaseRecord = new PurchaseRecord(PURCHASE_ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);
    when(purchaseRepository.save(any(PurchaseRecord.class))).thenReturn(purchaseRecord);

    // Act
    Purchase savedPurchase = purchaseGateway.save(purchase);

    // Assert
    assertEquals(purchase.id(), savedPurchase.id());
    assertEquals(purchase.description(), savedPurchase.description());
    assertEquals(purchase.purchaseDate(), savedPurchase.purchaseDate());
    assertEquals(purchase.amount(), savedPurchase.amount());
  }

  @Test
  void testFindById() {
    // Arrange
    var purchase = Purchase.of(PURCHASE_ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);
    var purchaseRecord = new PurchaseRecord(PURCHASE_ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);
    when(purchaseRepository.findById(PURCHASE_ID)).thenReturn(Optional.of(purchaseRecord));

    // Act
    Optional<Purchase> foundPurchase = purchaseGateway.findById(PURCHASE_ID);

    // Assert
    assertTrue(foundPurchase.isPresent());
    assertEquals(purchase.id(), foundPurchase.get().id());
    assertEquals(purchase.description(), foundPurchase.get().description());
    assertEquals(purchase.purchaseDate(), foundPurchase.get().purchaseDate());
    assertEquals(purchase.amount(), foundPurchase.get().amount());
  }
}
