package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import java.util.Optional;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRecordRepository extends CrudRepository<PurchaseRecord, Long> {
}
