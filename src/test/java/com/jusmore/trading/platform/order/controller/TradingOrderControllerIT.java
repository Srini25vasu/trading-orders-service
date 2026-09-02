package com.jusmore.trading.platform.order.controller;

import com.jusmore.trading.platform.common.config.AbstractIntegrationTest;
import com.jusmore.trading.platform.order.repository.TradingOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;

public class TradingOrderControllerIT extends AbstractIntegrationTest {
  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  TradingOrderRepository tradingOrderRepository;

  @BeforeEach
  void setup() {
    tradingOrderRepository.deleteAll();
  }

  @Test
  void shouldCreateAndPersistTradingOrder() {



  }

}

