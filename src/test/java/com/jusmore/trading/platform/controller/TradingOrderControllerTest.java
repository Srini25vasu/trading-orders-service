package com.jusmore.trading.platform.controller;

import com.jusmore.trading.platform.config.TestSecurityConfig;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.exception.ErrorCode;
import com.jusmore.trading.platform.exception.ErrorResponse;
import com.jusmore.trading.platform.exception.ResourceNotFoundException;
import com.jusmore.trading.platform.service.TradingOrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TradingOrderController.class)
@Import(TestSecurityConfig.class)
class TradingOrderControllerTest {
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
  }

  @AfterEach
  void tearDown() {
    sampleOrders = null;
  }

  /**
   * https://rieckpil.de/how-to-test-spring-boot-web-controller-the-right-way/
   * 2. Status Codes and Headers
   * MockMvc gives you full control to check HTTP-specific details:
   *
   * Status: expect(status().isOk()) or expect(status().isForbidden()).
   * Headers: expect(header().string("X-Custom-Header", "value")).
   * Content Type: expect(content().contentType(MediaType.APPLICATION_JSON)).
   *
   *  // Act & Assert
   *     mockMvc.perform(get("/api/users/1")
   *       .accept(MediaType.APPLICATION_JSON))
   *       .andExpect(status().isOk())
   *       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
   *       .andExpect(jsonPath("$.name").value("Alice"));
   */

  @Test
  void testGetAllOrders() throws Exception {
    when(tradingOrderService.getAllOrders()).thenReturn(sampleOrders);
    mockMvc.perform(get("/api/v1/orders"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(sampleOrders)));
  }

  /**
   *    // When & Then: We call the API and verify the response
   *         mockMvc.perform(get("/api/jobs")
   *                         .param("query", "Java")
   *                         .contentType(MediaType.APPLICATION_JSON))
   *                 .andExpect(status().isOk())
   *                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
   *                 .andExpect(jsonPath("$[0].title").value("Java Developer"))
   *                 .andExpect(jsonPath("$[1].title").value("Java Architect"))
   *                 .andExpect(jsonPath("$").isArray())
   *                 .andExpect(jsonPath("$.length()").value(2));
   * @throws Exception
   */

  @Test
  void testGetAllOrdersInternlError() throws Exception {
    when(tradingOrderService.getAllOrders()).thenThrow(new RuntimeException("Internal server error"));
    mockMvc.perform(get("/api/v1/orders"))
          .andExpect(status().isInternalServerError());
  }

  @Test
  void testGetOrderById() throws Exception {
    when(tradingOrderService.getOrder(1L)).thenReturn(sampleOrders.get(0));
    mockMvc.perform(get("/api/v1/orders/1"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(sampleOrders.get(0))));
  }

  @Test
  void testGetOrderByIdNotFound() throws Exception {
    when(tradingOrderService.getOrder(1L)).thenThrow(new ResourceNotFoundException("Trading order not found with id: 1"));
    mockMvc.perform(get("/api/v1/orders/1"))
          .andExpect(status().isNotFound())
    .andExpect(content().json(objectMapper.writeValueAsString(getErrorResponseNotFound(1L))));
  }

  @Test
  void testGetOrderByIdNegative() throws Exception {
    mockMvc.perform(get("/api/v1/orders/-1"))
          .andExpect(status().isBadRequest())
    .andExpect(content().json(objectMapper.writeValueAsString(getErrorResponseInvalid(-1L))));
  }

  @Test
  void testGetOrderByIdNullString() throws Exception {
    mockMvc.perform(get("/api/v1/orders/null"))
          .andExpect(status().isInternalServerError());
  }

  private ErrorResponse getErrorResponseNotFound(Long id) {
    return ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .code(ErrorCode.RESOURCE_NOT_FOUND)
        .message("Trading order not found with id: " + id)
        .timestamp(LocalDate.now().toString())
        .build();
  }

  private ErrorResponse   getErrorResponseInvalid(Object paramValue) {
    switch(paramValue) {
      case Long value -> {
        return ErrorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .code(ErrorCode.VALIDATION_ERROR)
              .message("getOrder.id: Order ID must be positive")
              .timestamp(LocalDate.now().toString())
              .build();
      }
      default -> {
        return ErrorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .code(ErrorCode.VALIDATION_ERROR)
              .message("Invalid trading order id:" + paramValue)
              .timestamp(LocalDate.now().toString())
              .build();
      }
    }






  }
}
