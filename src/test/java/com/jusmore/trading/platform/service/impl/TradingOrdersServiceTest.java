package com.jusmore.trading.platform.service.impl;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.dto.response.TradeOrderSummary;
import com.jusmore.trading.platform.repository.entities.TradingOrderRepository;
import com.jusmore.trading.platform.mapper.TradingOrderMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jusmore.trading.platform.exception.ResourceNotFoundException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradingOrdersServiceTest {
  @Mock
  private TradingOrderRepository tradingOrderRepository;

  @Mock
  private TradingOrderMapper tradingOrderMapper;

  @InjectMocks
  private TradingOrderServiceImpl tradingOrdersService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private List<TradingOrder> tradingOrders;
  private List<OrderResponse> orderResponses;
  private List<TradeOrderSummary> tradeOrderSummaries;

  @BeforeEach
  void setup() throws IOException {
    tradingOrders = objectMapper.readValue(getClass()
          .getResourceAsStream("/trading-orders.json"), objectMapper.getTypeFactory()
          .constructCollectionType(List.class, TradingOrder.class));
    orderResponses = objectMapper.readValue(getClass()
          .getResourceAsStream("/order-responses.json"), objectMapper.getTypeFactory()
          .constructCollectionType(List.class, OrderResponse.class));
    tradeOrderSummaries = objectMapper.readValue(getClass()
          .getResourceAsStream("/trade-order-summaries.json"), objectMapper.getTypeFactory()
          .constructCollectionType(List.class, TradeOrderSummary.class));
  }

  @Test
  void testCreateOrder() {
    CreateOrderRequest createOrderRequest = new CreateOrderRequest(
          "SYM",23.36,27.29,"UP"
    );
    when(tradingOrderMapper.toTradingOrder(createOrderRequest)).thenReturn(tradingOrders.get(0));
    when(tradingOrderRepository.save(tradingOrders.get(0))).thenReturn(tradingOrders.get(0));
    when(tradingOrderMapper.toOrderResponse(tradingOrders.get(0))).thenReturn(orderResponses.get(0));

    OrderResponse actual = tradingOrdersService.createOrder(createOrderRequest);
    assertEquals(orderResponses.get(0),actual);
    verify(tradingOrderRepository).save(tradingOrders.get(0));
    verify(tradingOrderMapper).toTradingOrder(createOrderRequest);
    verify(tradingOrderMapper).toOrderResponse(tradingOrders.get(0));
  }

  /**
   * https://tech-talk.the-experts.nl/mastering-void-method-testing-with-mockito-in-spring-boot-applications-4f19e0a6259d
   */

  @Test
  void testUpdateOrder() {
    UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(
          23.36,27.29,"UP"
    );

    TradingOrder existingTradingOrder = new TradingOrder();
    existingTradingOrder.setId(1L);
    existingTradingOrder.setSymbol("SYM");
    existingTradingOrder.setPrice(20.36);
    existingTradingOrder.setQuantity(37.29);
    existingTradingOrder.setSide("DOWN");

    when(tradingOrderRepository.findById(1L)).thenReturn(Optional.of(existingTradingOrder));

    doAnswer(invocation -> {
      UpdateOrderRequest updateOrderReq = invocation.getArgument(0);
      TradingOrder existingOrder = invocation.getArgument(1);
      existingOrder.setPrice(updateOrderReq.price());
      existingOrder.setQuantity(updateOrderReq.quantity());
      existingOrder.setSide(updateOrderReq.side());
      return null;
    }).when(tradingOrderMapper).updateTradingOrderFromRequest(updateOrderRequest, existingTradingOrder);

    when(tradingOrderRepository.save(existingTradingOrder)).thenReturn(existingTradingOrder);
    when(tradingOrderMapper.toOrderResponse(existingTradingOrder)).thenReturn(orderResponses.get(0));

    OrderResponse actual = tradingOrdersService.updateOrder(1L, updateOrderRequest);
    assertEquals(orderResponses.get(0), actual);
    verify(tradingOrderRepository).save(existingTradingOrder);
    verify(tradingOrderMapper).updateTradingOrderFromRequest(updateOrderRequest, existingTradingOrder);
    verify(tradingOrderMapper).toOrderResponse(existingTradingOrder);
  }
  @Test
  void testUpdateOrderNotFound() {
    UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(
          23.36,27.29,"UP"
    );
    when(tradingOrderRepository.findById(1L)).thenReturn(Optional.empty());
    ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tradingOrdersService.updateOrder(1L, updateOrderRequest));
    assertEquals("Trading order not found with id: 1", ex.getMessage());
  }

  @Test
  void testDeleteOrder() {
    when(tradingOrderRepository.existsById(1L)).thenReturn(true);
    tradingOrdersService.deleteOrder(1L);
    verify(tradingOrderRepository).deleteById(1L);
  }

  @Test
  void testDeleteOrderNotFound() {
    when(tradingOrderRepository.existsById(1L)).thenReturn(false);
    ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tradingOrdersService.deleteOrder(1L));
    assertEquals("Trading order not found with id: 1", ex.getMessage());
  }

  @Test
  void testGetAllOrders() {
    when(tradingOrderRepository.findAll()).thenReturn(tradingOrders);
    when(tradingOrderMapper.toOrderResponseList(tradingOrders)).thenReturn(orderResponses);

    List<OrderResponse> actual = tradingOrdersService.getAllOrders();

    assertEquals(orderResponses, actual);
    verify(tradingOrderRepository).findAll();
    verify(tradingOrderMapper).toOrderResponseList(tradingOrders);
  }

  @Test
  void testGetOrder() {
    when(tradingOrderRepository.findById(1L)).thenReturn(Optional.of(tradingOrders.get(0)));
    when(tradingOrderMapper.toOrderResponse(tradingOrders.get(0))).thenReturn(orderResponses.get(0));

    OrderResponse actual = tradingOrdersService.getOrder(1L);

    assertEquals(orderResponses.get(0), actual);
  }

  @Test
  void testGetOrderNotFound() {
    when(tradingOrderRepository.findById(99L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
        () -> tradingOrdersService.getOrder(99L));

    assertEquals("Trading order not found with id: 99", exception.getMessage());
    verify(tradingOrderRepository).findById(99L);
    verifyNoInteractions(tradingOrderMapper);
  }

  @Test
  void testGetOrderInvalidOrderId() {
    when(tradingOrderRepository.findById(-1L)).thenThrow(new ConstraintViolationException("Invalid order ID", null));

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
        () -> tradingOrdersService.getOrder(-1L));

    assertEquals("Invalid order ID", exception.getMessage());
    verify(tradingOrderRepository).findById(-1L);
    verifyNoInteractions(tradingOrderMapper);
  }

  @Test
  void testGetTradeOrderSummaries() {


  }
}
