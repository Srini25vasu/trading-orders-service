package com.jusmore.trading.platform.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
      @NotNull
      String symbol,
      double quantity,
      double price,
      @NotNull
      String side
) {
}

