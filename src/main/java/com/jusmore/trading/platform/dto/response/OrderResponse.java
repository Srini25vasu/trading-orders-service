package com.jusmore.trading.platform.dto.response;

public record OrderResponse(
      Long id,
      String symbol,
      double quantity,
      double price,
      String side
) {
}
