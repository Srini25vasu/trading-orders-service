package com.jusmore.trading.platform.dto.response;

import lombok.Builder;

@Builder
public record OrderResponse(
      Long id,
      String symbol,
      double quantity,
      double price,
      String side
) {

}
