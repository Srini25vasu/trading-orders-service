package com.jusmore.trading.platform.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
            String symbol,
      double quantity,
      double price,
      String side
) {
}
