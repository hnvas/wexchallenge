package com.hnvas.wexchagellenge.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesCommand;
import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesCommandHandler;
import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesOutput;
import com.hnvas.wexchagellenge.infrastructure.api.ExchangeApi;
import com.hnvas.wexchagellenge.infrastructure.monitoring.ExecutionRecorder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ExchangeController implements ExchangeApi {

  private final GetExchangesCommandHandler getExchangesCommandHandler;
  private final ExecutionRecorder executionRecorder;

  public ExchangeController(
      GetExchangesCommandHandler getExchangesCommandHandler, ExecutionRecorder executionRecorder) {
    this.getExchangesCommandHandler = getExchangesCommandHandler;
    this.executionRecorder = executionRecorder;
  }

  @Override
  public ResponseEntity<GetExchangesOutput> getExchanges(
      Long id, String countryCurrency, String currency) {
    log.info(
        "Received request to get exchanges for id: {}, countryCurrency: {}, currency: {}",
        id,
        countryCurrency,
        currency);
    return ResponseEntity.ok(
        executionRecorder.executeWithTiming(
            () ->
                getExchangesCommandHandler.handle(
                    new GetExchangesCommand(id, countryCurrency, currency))));
  }
}
