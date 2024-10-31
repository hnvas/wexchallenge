package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;
import org.springframework.test.context.jdbc.Sql;

@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Sql(
    scripts = {"classpath:db/cleanup.sql"},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PurchaseGatewayRepositoryTest {

  private static final Long PURCHASE_ID = 1L;
  private static final String DESCRIPTION = "Test Description";
  private static final LocalDate PURCHASE_DATE = LocalDate.of(2023, 1, 1);
  private static final BigDecimal AMOUNT = new BigDecimal("100.00");

  @Autowired private PurchaseRecordRepository purchaseRepository;

  @BeforeEach
  void setUp() {
    purchaseRepository.deleteAll();
  }

  private static PurchaseRecord givenPurchase() {
    return new PurchaseRecord(PURCHASE_ID, DESCRIPTION, PURCHASE_DATE, AMOUNT);
  }

  @Test
  void testSave() {
    // Act
    PurchaseRecord purchaseRecord = givenPurchase();
    PurchaseRecord savedRecord = purchaseRepository.save(purchaseRecord);

    // Assert
    assertEquals(purchaseRecord.getId(), savedRecord.getId());
    assertEquals(purchaseRecord.getDescription(), savedRecord.getDescription());
    assertEquals(purchaseRecord.getPurchaseDate(), savedRecord.getPurchaseDate());
    assertEquals(purchaseRecord.getAmount(), savedRecord.getAmount());
  }

  @Test
  void testFindById() {
    // Arrange
    PurchaseRecord purchaseRecord = givenPurchase();
    PurchaseRecord savedRecord = purchaseRepository.save(purchaseRecord);

    // Act
    Optional<PurchaseRecord> foundPurchase = purchaseRepository.findById(savedRecord.getId());

    // Assert
    assertTrue(foundPurchase.isPresent());
    assertEquals(savedRecord.getId(), foundPurchase.get().getId());
    assertEquals(savedRecord.getDescription(), foundPurchase.get().getDescription());
    assertEquals(savedRecord.getPurchaseDate(), foundPurchase.get().getPurchaseDate());
    assertEquals(savedRecord.getAmount(), foundPurchase.get().getAmount());
  }
}