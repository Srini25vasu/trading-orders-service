package com.jusmore.trading.platform.customer.dto.response;

public record CustomerOrderResponse(
      String symbol,
      double quantity,
      double price,
      String side
) {
}
