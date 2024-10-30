package com.hnvas.wexchagellenge.application.purchase.create;

import java.util.Set;

import com.hnvas.wexchagellenge.application.validation.ValidationException;
import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;

import jakarta.validation.ConstraintViolation;

public class CreatePurchaseHandler {

  private static final String INVALID_PURCHASE_MESSAGE = "Invalid purchase data";

  private final PurchaseGateway purchaseGateway;
  private final ValidationHandler<CreatePurchaseCommand> validationHandler;

  public CreatePurchaseHandler(
      PurchaseGateway purchaseGateway, ValidationHandler<CreatePurchaseCommand> validationHandler) {
    this.purchaseGateway = purchaseGateway;
    this.validationHandler = validationHandler;
  }

  private static ValidationException invalidCreatePurchaseCommand(
      Set<ConstraintViolation<CreatePurchaseCommand>> violations) {
    return new ValidationException(
        INVALID_PURCHASE_MESSAGE,
        violations.stream().map(ConstraintViolation::getMessage).toList());
  }

  public CreatePurchaseOutput handle(CreatePurchaseCommand command) {
    if (!validationHandler.isValid(command)) {
      throw invalidCreatePurchaseCommand(validationHandler.violations());
    }

    return CreatePurchaseOutput.fromModel(purchaseGateway.save(command.toModel()));
  }
}
