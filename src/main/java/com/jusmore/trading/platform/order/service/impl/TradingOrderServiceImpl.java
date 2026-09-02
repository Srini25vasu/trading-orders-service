package com.jusmore.trading.platform.order.service.impl;

import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.repository.CustomerRepository;
import com.jusmore.trading.platform.order.domain.TradingOrder;
import com.jusmore.trading.platform.order.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.order.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.order.dto.response.OrderResponse;
import com.jusmore.trading.platform.order.dto.response.TradeOrderSummary;
import com.jusmore.trading.platform.common.exception.ResourceNotFoundException;
import com.jusmore.trading.platform.order.mapper.TradingOrderMapper;
import com.jusmore.trading.platform.order.repository.TradingOrderRepository;
import com.jusmore.trading.platform.order.service.TradingOrderService;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TradingOrderServiceImpl implements TradingOrderService {
  private final TradingOrderRepository tradingOrderRepository;
  private final CustomerRepository customerRepository;
  private final TradingOrderMapper tradingOrderMapper;

  @Override
  @Transactional
  public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
    log.info("Erstellung des Trading-Orders für Kunden mit id: {}", createOrderRequest.customerId());
    try {
      Customer customer = customerRepository.findById(CustomerId.create(createOrderRequest.customerId()))
            .orElseThrow(() -> new ResourceNotFoundException("Kunde nicht gefunden mit id: " + createOrderRequest.customerId()));

      TradingOrder tradingOrder = tradingOrderMapper.toTradingOrder(createOrderRequest);
      tradingOrder.setCustomer(customer);
      TradingOrder createdTradingOrder = tradingOrderRepository.save(tradingOrder);
      OrderResponse orderResponse = tradingOrderMapper.toOrderResponse(createdTradingOrder);
      log.debug("Trading-Order: {} für Kunden mit id: {} erfolgreich erstellt", orderResponse, createOrderRequest.customerId());
      return orderResponse;
    } catch (Exception e) {
      log.error("Fehler beim Erstellen des Trading-Orders für Kunden mit id: {}", createOrderRequest.customerId(), e);
      throw e;
    }

  }

  @Override
  @Transactional(readOnly = true)
  public OrderResponse getOrder(Long id) {
    return tradingOrderRepository.findById(id)
          .map(tradingOrderMapper::toOrderResponse)
          .orElseThrow(() -> new ResourceNotFoundException("Trading order not found with id: " + id));
  }

  @Override
  @Transactional
  public OrderResponse updateOrder(Long id, UpdateOrderRequest updateOrderRequest) {
    TradingOrder existingOrder = tradingOrderRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Trading order not found with id: " + id));
    tradingOrderMapper.updateTradingOrderFromRequest(updateOrderRequest, existingOrder);
    TradingOrder savedOrder = tradingOrderRepository.save(existingOrder);
    return tradingOrderMapper.toOrderResponse(savedOrder);
  }

  @Override
  @Transactional
  public void deleteOrder(Long id) {
    try {
      tradingOrderRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResourceNotFoundException("Trading order not found with id: " + id);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderResponse> getAllOrders() {
    return tradingOrderMapper.toOrderResponseList(tradingOrderRepository.findAll());
  }

  @Override
  public OrderResponse updateTradingOrder(Long id, UpdateOrderRequest updateOrderRequest) {
    TradingOrder existingOrder = tradingOrderRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Trading order with id: " + id + " not found"));
    tradingOrderMapper.updateTradingOrder(updateOrderRequest, existingOrder);
    TradingOrder savedOrder = tradingOrderRepository.save(existingOrder);
    return tradingOrderMapper.toOrderResponse(savedOrder);
  }

  @Override
  public List<OrderResponse> getOrders(String symbol, String side, String groupBy) {
    return tradingOrderMapper.toOrderResponseList(tradingOrderRepository.findOrders(symbol, side, groupBy));
  }

  @Override
  public List<TradeOrderSummary> getTradeOrderSummaries(String slide) {
    return tradingOrderRepository.findTradeOrderSummaries(slide);
  }
}
