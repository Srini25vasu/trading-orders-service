package com.jusmore.trading.platform.dto.request;

public record CreateOrderRequest(
      String symbol,
      double quantity,
      double price,
      String side
) {
}
