package com.hnvas.wexchagellenge.infrastructure.persistence.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description", nullable = false, length = 50)
  private String description;

  @Column(name = "effective_date", nullable = false)
  private LocalDate purchaseDate;

  @Column(name = "amount", nullable = false, precision = 14, scale = 2)
  private BigDecimal amount;

  public Purchase toModel() {
    return Purchase.of(id, description, purchaseDate, amount);
  }

  public static PurchaseRecord fromModel(Purchase purchase) {
    return new PurchaseRecord(
        purchase.id(), purchase.description(), purchase.purchaseDate(), purchase.amount());
  }
}
