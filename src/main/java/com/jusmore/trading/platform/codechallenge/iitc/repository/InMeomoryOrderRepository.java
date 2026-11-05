package com.jusmore.trading.platform.codechallenge.iitc.repository;

import com.jusmore.trading.platform.codechallenge.iitc.model.Order;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * One concrete and swappable implementation of OrderRepository
 * Its only responsibility is to store and retrieve orders and no business rules live here
 */

public class InMeomoryOrderRepository implements OrderRepository{

  Map<String, Order> store = new LinkedHashMap<>();

  @Override
  public Order save(Order order) {
    store.put(order.getOrderId(), order);
    return order;
  }

  @Override
  public Optional<Order> findById(String orderId) {
    return Optional.ofNullable(store.get(orderId));
  }

  @Override
  public List<Order> findAll() {
    return List.copyOf(store.values());
  }
}
