package com.jusmore.trading.platform.codechallenge.iitc.service;

import com.jusmore.trading.platform.codechallenge.iitc.model.Order;
import com.jusmore.trading.platform.codechallenge.iitc.model.OrderItem;
import com.jusmore.trading.platform.codechallenge.iitc.payment.PaymentStrategy;

import java.util.List;

public interface OrderService {
  Order createOrder(String orderId,String customerId, List<OrderItem> items);
  Order placeOrder(String orderId, PaymentStrategy paymentStrategy);
  Order getOrderById(String orderId);
  List<Order> getAllOrders();
}
