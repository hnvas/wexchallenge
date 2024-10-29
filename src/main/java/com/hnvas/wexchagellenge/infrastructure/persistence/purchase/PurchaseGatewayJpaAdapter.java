package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PurchaseGatewayJpaAdapter implements PurchaseGateway {

  private final PurchaseRepository purchaseRepository;

  public PurchaseGatewayJpaAdapter(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  @Override
  public Purchase save(Purchase purchase) {
    PurchaseRecord purchaseRecord = purchaseRepository.save(PurchaseRecord.fromModel(purchase));
    return purchaseRecord.toModel();
  }

  @Override
  public Optional<Purchase> findById(Long id) {
    return purchaseRepository.findById(id).map(PurchaseRecord::toModel);
  }
}
