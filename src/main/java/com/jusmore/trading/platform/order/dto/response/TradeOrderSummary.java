package com.jusmore.trading.platform.order.dto.response;

public record TradeOrderSummary(
      String symbol,
      long totalOrders,
      double totalQuantity,
      double totalAmount
) {
}

