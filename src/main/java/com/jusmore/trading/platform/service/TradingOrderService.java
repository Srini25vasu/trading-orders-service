package com.jusmore.trading.platform.service;

import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;

import java.util.List;

public interface TradingOrderService {
  OrderResponse createOrder(CreateOrderRequest createOrderRequest);
  OrderResponse getOrder(Long id);
  OrderResponse updateOrder(Long id, CreateOrderRequest createOrderRequest);
  void deleteOrder(Long id);
  List<OrderResponse> getAllOrders();
}
