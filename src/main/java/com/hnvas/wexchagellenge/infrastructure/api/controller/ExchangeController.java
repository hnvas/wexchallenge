package com.hnvas.wexchagellenge.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesCommand;
import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesCommandHandler;
import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesOutput;
import com.hnvas.wexchagellenge.infrastructure.api.ExchangeApi;

@RestController
public class ExchangeController implements ExchangeApi {

  private final GetExchangesCommandHandler getExchangesCommandHandler;

  public ExchangeController(GetExchangesCommandHandler getExchangesCommandHandler) {
    this.getExchangesCommandHandler = getExchangesCommandHandler;
  }

  @Override
  public ResponseEntity<GetExchangesOutput> getExchanges(
      Long id, String countryCurrency, String currency) {
    return ResponseEntity.ok(
        getExchangesCommandHandler.handle(new GetExchangesCommand(id, countryCurrency, currency)));
  }
}
