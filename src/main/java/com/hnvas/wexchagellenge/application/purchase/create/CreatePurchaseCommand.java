package com.hnvas.wexchagellenge.application.purchase.create;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.hnvas.wexchagellenge.domain.purchase.Purchase;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Validated
public record CreatePurchaseCommand(
    @NotNull(message = "Description is required")
        @NotEmpty(message = "Description cannot be empty")
        @Size(max = 50, message = "Description must be between 20 and 50 characters")
        String description,
    @NotNull(message = "Purchase date is required")
        @PastOrPresent(message = "Purchase date must be in the past or present")
        LocalDate purchaseDate,
    @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        @Digits(
            integer = 14,
            fraction = 2,
            message = "Amount must have up to 14 digits and 2 decimals")
        BigDecimal amount) {
  public Purchase toModel() {
    return Purchase.create(description, purchaseDate, amount);
  }
}
