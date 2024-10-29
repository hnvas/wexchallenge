package com.hnvas.wexchagellenge.domain.purchase;

import java.util.Optional;

public interface PurchaseGateway {

  Purchase save(Purchase purchase);

  Optional<Purchase> findById(Long id);
}
