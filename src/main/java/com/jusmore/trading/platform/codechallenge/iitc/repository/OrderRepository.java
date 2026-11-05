package com.jusmore.trading.platform.codechallenge.iitc.repository;

import com.jusmore.trading.platform.codechallenge.iitc.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
  Order save(Order order);
  Optional<Order> findById(String orderId);
  List<Order> findAll();
}
