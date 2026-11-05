package com.jusmore.trading.platform.codechallenge.iitc.model;

import lombok.Getter;

import java.util.List;

/**
 * Single responsibility: Order only knows how to hold and total its own items
 */
@Getter
public final class Order {
  private final String orderId;
  private final String customerId;
  private final List<OrderItem> items;
  private OrderStatus status;
  private double discountApplied;

  public Order(String orderId, String customerId,List<OrderItem> items){
    if(orderId == null || orderId.isBlank()){
      throw new IllegalArgumentException("Order ID cannot be null or blank");
    }
    if(customerId == null || customerId.isBlank()){
      throw new IllegalArgumentException("Customer ID cannot be null or blank");
    }
    if(items == null || items.isEmpty()){
      throw new IllegalArgumentException("Order must have at least one item");
    }
    this.orderId = orderId;
    this.customerId = customerId;
    this.items = items;
    this.status = OrderStatus.PENDING;
    this.discountApplied = 0;
  }

  public double getSubTotal() {
    return items.stream().mapToDouble(OrderItem::price).sum();
  }
  public double getTotal() {
    return getSubTotal() - discountApplied;
  }
  public void setStatus(OrderStatus status) {
    this.status = status;
  }
}
