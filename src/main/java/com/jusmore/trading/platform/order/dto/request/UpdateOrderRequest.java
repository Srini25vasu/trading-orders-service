package com.jusmore.trading.platform.order.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateOrderRequest(
      @NotNull
      @Positive
      @Max(value = 10000, message = "Price must be less than or equal to 10,000")
      double price,
      @NotNull
      @Positive
      @Max(value = 1000, message = "Quantity must be less than or equal to 1,000")
      double quantity,
      String side
) {
}

