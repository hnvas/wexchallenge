package com.hnvas.wexchagellenge.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseCommand;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseHandler;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseOutput;
import com.hnvas.wexchagellenge.infrastructure.api.PurchaseApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController implements PurchaseApi {

  private final CreatePurchaseHandler createPurchaseHandler;

  public PurchaseController(CreatePurchaseHandler createPurchaseHandler) {
    this.createPurchaseHandler = createPurchaseHandler;
  }

  @Override
  public ResponseEntity<CreatePurchaseOutput> createPurchase(
      CreatePurchaseCommand createPurchaseCommand) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createPurchaseHandler.handle(createPurchaseCommand));
  }
}
