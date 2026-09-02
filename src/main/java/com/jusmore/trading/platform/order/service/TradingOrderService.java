package com.jusmore.trading.platform.order.service;

import com.jusmore.trading.platform.order.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.order.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.order.dto.response.OrderResponse;
import com.jusmore.trading.platform.order.dto.response.TradeOrderSummary;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface TradingOrderService {
  OrderResponse createOrder(CreateOrderRequest createOrderRequest);
  OrderResponse getOrder(@NotNull(message = "Order ID cannot be null") @Positive(message = "Order ID must be positive") Long id);
  OrderResponse updateOrder(Long id, UpdateOrderRequest updateOrderRequest);
  void deleteOrder(@NotNull(message = "Order ID cannot be null") @Positive(message = "Order ID must be positive") Long id);
  List<OrderResponse> getAllOrders();
  OrderResponse updateTradingOrder(Long id, UpdateOrderRequest updateOrderRequest );
  List<OrderResponse> getOrders(String symbol, String side, String groupBy);
  List<TradeOrderSummary> getTradeOrderSummaries(String slide);
}

