package com.jusmore.trading.platform.order.dto.response;

public record OrderResponse(
      Long id,
      String symbol,
      double quantity,
      double price,
      String side,
      String customerName
) {

}

