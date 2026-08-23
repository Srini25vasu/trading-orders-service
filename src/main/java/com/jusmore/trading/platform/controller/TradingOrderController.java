package com.jusmore.trading.platform.controller;

import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.service.TradingOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Validated
public class TradingOrderController {
  private final TradingOrderService tradingOrderService;

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(tradingOrderService.createOrder(createOrderRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable @Positive(message = "Order ID must be positive") Long id) {
    return ResponseEntity.ok(tradingOrderService.getOrder(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponse> updateOrder(
        @PathVariable @Positive(message = "Order ID must be positive") Long id,
        @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
    return ResponseEntity.ok(tradingOrderService.updateOrder(id, updateOrderRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable @Positive(message = "Order ID must be positive") Long id) {
    tradingOrderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getAllOrders() {
    return ResponseEntity.ok(tradingOrderService.getAllOrders());
  }
}
