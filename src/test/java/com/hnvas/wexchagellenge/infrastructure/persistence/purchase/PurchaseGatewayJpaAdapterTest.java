package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PurchaseGatewayJpaAdapterTest {

  @Autowired
  private PurchaseGatewayJpaAdapter purchaseGatewayJpaAdapter;

  @Autowired
  private PurchaseRepository purchaseRepository;

  private Purchase purchase;

  @BeforeEach
  void setUp() {
    purchaseRepository.deleteAll();
    purchase = Purchase.of(1L, "Test Description", LocalDate.of(2023, 1, 1), new BigDecimal("100.00"));
  }

  @Test
  void testSave() {
    // Act
    Purchase savedPurchase = purchaseGatewayJpaAdapter.save(purchase);

    // Assert
    assertEquals(purchase.id(), savedPurchase.id());
    assertEquals(purchase.description(), savedPurchase.description());
    assertEquals(purchase.purchaseDate(), savedPurchase.purchaseDate());
    assertEquals(purchase.amount(), savedPurchase.amount());
  }

  @Test
  void testFindById() {
    // Arrange
    PurchaseRecord savedRecord = purchaseRepository.save(PurchaseRecord.fromModel(purchase));

    // Act
    Optional<Purchase> foundPurchase = purchaseGatewayJpaAdapter.findById(savedRecord.getId());

    // Assert
    assertTrue(foundPurchase.isPresent());
    assertEquals(savedRecord.getId(), foundPurchase.get().id());
    assertEquals(savedRecord.getDescription(), foundPurchase.get().description());
    assertEquals(savedRecord.getPurchaseDate(), foundPurchase.get().purchaseDate());
    assertEquals(savedRecord.getAmount(), foundPurchase.get().amount());
  }
}