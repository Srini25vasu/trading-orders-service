package com.jusmore.trading.platform.codechallenge.iitc.service;

import com.jusmore.trading.platform.codechallenge.iitc.model.Order;
import com.jusmore.trading.platform.codechallenge.iitc.model.OrderItem;
import com.jusmore.trading.platform.codechallenge.iitc.model.OrderStatus;
import com.jusmore.trading.platform.codechallenge.iitc.payment.PaymentStrategy;
import com.jusmore.trading.platform.codechallenge.iitc.repository.OrderRepository;

import java.util.List;

/**
 *  Dependency Inversion Principle in action: this HIGH-LEVEL policy class depends
 *  * only on the LOW-LEVEL abstractions OrderRepository and NotificationService —
 *  * both interfaces, injected through the constructor. It never instantiates
 *  * "new InMemoryOrderRepository()" or "new EmailNotificationService()" itself.
 *  *
 *  * PaymentStrategy and DiscountStrategy are supplied per-call, which is what
 *  * lets this class stay Closed for Modification (OCP) while remaining Open for
 *  * Extension: brand new payment methods or discount rules never require editing
 *  * this file.
 */
public class OrderServiceImpl implements OrderService{

  private final OrderRepository orderRepository;

  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public Order createOrder(String orderId, String customerId, List<OrderItem> items) {
    Order order = new Order(orderId,customerId,items);
    orderRepository.save(order);
    return order;
  }

  @Override
  public Order placeOrder(String orderId, PaymentStrategy paymentStrategy) {
    Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    boolean paid = paymentStrategy.pay(order.getTotal());
    if(!paid) {
      throw new RuntimeException("Payment failed");
    }
    order.setStatus(OrderStatus.PLACED);
    orderRepository.save(order);
    return order;
  }

  @Override
  public Order getOrderById(String orderId) {
    return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
  }

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }
}
