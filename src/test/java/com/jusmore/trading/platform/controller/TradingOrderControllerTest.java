package com.jusmore.trading.platform.controller;

import com.jusmore.trading.platform.config.TestSecurityConfig;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.service.TradingOrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TradingOrderController.class)
@Import(TestSecurityConfig.class)
public class TradingOrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TradingOrderService tradingOrderService;

  private ObjectMapper objectMapper = new ObjectMapper();

  List<OrderResponse> sampleOrders;

  @BeforeEach
  void setup() throws Exception {
    sampleOrders = objectMapper.readValue(
        getClass().getResourceAsStream("/trading-orders.json"),
        objectMapper.getTypeFactory().constructCollectionType(List.class, OrderResponse.class)
    );
    when(tradingOrderService.getAllOrders()).thenReturn(sampleOrders);
  }

  @AfterEach
  void tearDown() {
    sampleOrders = null;
  }

  @Test
  void testGetAllOrders() throws Exception {
    mockMvc.perform(get("/api/v1/orders"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(sampleOrders)));
  }

  List<OrderResponse> getSampleOrders() {
    OrderResponse order1 = OrderResponse.builder()
        .id(1L)
        .symbol("AAPL")
        .quantity(10)
        .price(150.0)
        .side("BUY")
        .build();

    OrderResponse order2 = OrderResponse.builder()
        .id(2L)
        .symbol("GOOGL")
        .quantity(5)
        .price(2800.0)
        .side("SELL")
        .build();
    OrderResponse order3 = OrderResponse.builder()
        .id(3L)
        .symbol("MSFT")
        .quantity(8)
        .price(300.0)
        .side("BUY")
        .build();

    return List.of(order1, order2, order3);
  }
}
