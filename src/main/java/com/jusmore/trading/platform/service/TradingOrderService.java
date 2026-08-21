package com.jusmore.trading.platform.service;

import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface TradingOrderService {
  OrderResponse createOrder(CreateOrderRequest createOrderRequest);
  OrderResponse getOrder(@NotNull(message = "Order ID cannot be null") @Positive(message = "Order ID must be positive") Long id);
  OrderResponse updateOrder(@NotNull(message = "Order ID cannot be null") @Positive(message = "Order ID must be positive") Long id, CreateOrderRequest createOrderRequest);
  void deleteOrder(@NotNull(message = "Order ID cannot be null") @Positive(message = "Order ID must be positive") Long id);
  List<OrderResponse> getAllOrders();
}
