package com.hnvas.wexchagellenge.application.purchase.create;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

public record CreatePurchaseOutput(Long id) {
  public static CreatePurchaseOutput fromModel(Purchase purchase) {
    return new CreatePurchaseOutput(purchase.id());
  }
}
