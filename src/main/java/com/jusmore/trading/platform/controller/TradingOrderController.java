package com.jusmore.trading.platform.controller;

import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.service.TradingOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class TradingOrderController {
  private final TradingOrderService tradingOrderService;

  @PostMapping
  public OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
    return tradingOrderService.createOrder(createOrderRequest);
  }

  @GetMapping
  public List<OrderResponse> getAllOrders() {
    return tradingOrderService.getAllOrders();
  }
}
