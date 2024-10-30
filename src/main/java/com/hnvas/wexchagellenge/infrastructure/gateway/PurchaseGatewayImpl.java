package com.hnvas.wexchagellenge.infrastructure.gateway;

import java.util.Optional;

import com.hnvas.wexchagellenge.infrastructure.persistence.purchase.PurchaseRecord;
import com.hnvas.wexchagellenge.infrastructure.persistence.purchase.PurchaseRecordRepository;
import org.springframework.stereotype.Component;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;

@Component
public class PurchaseGatewayImpl implements PurchaseGateway {

  private final PurchaseRecordRepository purchaseRepository;

  public PurchaseGatewayImpl(PurchaseRecordRepository purchaseRepository) {
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
