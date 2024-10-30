package com.hnvas.wexchagellenge.application.purchase.create;

import com.hnvas.wexchagellenge.application.validation.ValidationException;
import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePurchaseHandlerTest {

  @Mock private PurchaseGateway purchaseGateway;

  @Mock private ValidationHandler<CreatePurchaseCommand> validationHandler;

  @InjectMocks private CreatePurchaseHandler createPurchaseHandler;

  @Test
  void testHandleValidCommand() {
    // Arrange
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("Valid Description")
            .purchaseDate(LocalDate.now())
            .amount(new BigDecimal("10.00"))
            .build();

    Purchase purchase =
        Purchase.of(1L, "Valid Description", LocalDate.now(), new BigDecimal("10.00"));

    when(validationHandler.isValid(command)).thenReturn(true);
    when(purchaseGateway.save(any(Purchase.class))).thenReturn(purchase);

    // Act
    CreatePurchaseOutput output = createPurchaseHandler.handle(command);

    // Assert
    assertNotNull(output);
    assertEquals(purchase.id(), output.id());
  }

  @Test
  void testHandleInvalidCommand() {
    // Arrange
    CreatePurchaseCommand command =
        CreatePurchaseCommand.builder()
            .description("")
            .purchaseDate(LocalDate.now().plusDays(1))
            .amount(new BigDecimal("0.00"))
            .build();

    Set<ConstraintViolation<CreatePurchaseCommand>> violations =
        Set.of(mock(ConstraintViolation.class), mock(ConstraintViolation.class));

    when(validationHandler.isValid(command)).thenReturn(false);
    when(validationHandler.violations()).thenReturn(violations);

    // Act & Assert
    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> {
              createPurchaseHandler.handle(command);
            });

    assertEquals("Invalid purchase information", exception.getMessage());
    assertEquals(violations.size(), exception.getViolations().size());
  }
}
