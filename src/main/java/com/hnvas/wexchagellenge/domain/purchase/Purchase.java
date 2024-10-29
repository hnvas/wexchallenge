package com.hnvas.wexchagellenge.domain.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Purchase {

  private Long id;
  private String description;
  private LocalDate purchaseDate;
  private BigDecimal amount;

  private Purchase(Long id, String description, LocalDate purchaseDate, BigDecimal amount) {
    this.id = id;
    this.description = description;
    this.purchaseDate = purchaseDate;
    this.amount = amount;
  }

  public static Purchase create(String description, LocalDate purchaseDate, BigDecimal amount) {
    return new Purchase(null, description, purchaseDate, amount);
  }

  public static Purchase of(Long id, String description, LocalDate purchaseDate, BigDecimal amount) {
    return new Purchase(id, description, purchaseDate, amount);
  }

  public Long id() {
    return id;
  }

  public String description() {
    return description;
  }

  public LocalDate purchaseDate() {
    return purchaseDate;
  }

  public BigDecimal amount() {
    return amount;
  }
}
