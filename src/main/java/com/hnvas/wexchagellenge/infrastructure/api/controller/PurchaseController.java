package com.hnvas.wexchagellenge.infrastructure.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseCommand;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseHandler;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseOutput;
import com.hnvas.wexchagellenge.infrastructure.api.PurchaseApi;
import com.hnvas.wexchagellenge.infrastructure.monitoring.ExecutionRecorder;

@Slf4j
@RestController
public class PurchaseController implements PurchaseApi {

  private final CreatePurchaseHandler createPurchaseHandler;
  private final ExecutionRecorder executionRecorder;

  public PurchaseController(
      CreatePurchaseHandler createPurchaseHandler, ExecutionRecorder executionRecorder) {
    this.createPurchaseHandler = createPurchaseHandler;
    this.executionRecorder = executionRecorder;
  }

  @Override
  public ResponseEntity<CreatePurchaseOutput> createPurchase(
      CreatePurchaseCommand createPurchaseCommand) {
    log.info("Received request to create purchase: {}", createPurchaseCommand);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            executionRecorder.executeWithTiming(
                () -> createPurchaseHandler.handle(createPurchaseCommand)));
  }
}
