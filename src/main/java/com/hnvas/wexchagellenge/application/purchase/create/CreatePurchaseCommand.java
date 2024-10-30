package com.hnvas.wexchagellenge.application.purchase.create;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Validated
public record CreatePurchaseCommand(
    @NotNull(message = "Description is required")
        @NotEmpty(message = "Description cannot be empty")
        @Size(max = 50, message = "Description must be between 20 and 50 characters")
        String description,
    @NotNull(message = "Purchase date is required") LocalDate purchaseDate,
    @NotNull(message = "Amount is required") BigDecimal amount) {
  public Purchase toModel() {
    return Purchase.create(description, purchaseDate, amount);
  }
}
