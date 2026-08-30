package com.jusmore.trading.platform.dto.response;

public record TradeOrderSummary(
      String symbol,
      long totalOrders,
      double totalQuantity,
      double totalAmount
) {
}
