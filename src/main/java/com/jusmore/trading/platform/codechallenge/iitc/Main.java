package com.jusmore.trading.platform.codechallenge.iitc;

import com.jusmore.trading.platform.codechallenge.iitc.repository.OrderRepository;
import com.jusmore.trading.platform.codechallenge.iitc.repository.InMeomoryOrderRepository;
import com.jusmore.trading.platform.codechallenge.iitc.service.OrderService;
import com.jusmore.trading.platform.codechallenge.iitc.service.OrderServiceImpl;

public class Main {
  static void main(String[] args) {
    //--wiring
    OrderRepository orderRepository = new InMeomoryOrderRepository();
    OrderService orderService = new OrderServiceImpl(orderRepository);


  }
}
