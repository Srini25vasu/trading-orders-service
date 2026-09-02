package com.jusmore.trading.platform.order.service.impl;

import com.jusmore.trading.platform.order.domain.TradingOrder;
import com.jusmore.trading.platform.order.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.order.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.order.dto.response.OrderResponse;
import com.jusmore.trading.platform.order.dto.response.TradeOrderSummary;
import com.jusmore.trading.platform.common.exception.ResourceNotFoundException;
import com.jusmore.trading.platform.order.mapper.TradingOrderMapper;
import com.jusmore.trading.platform.order.repository.TradingOrderRepository;
import com.jusmore.trading.platform.order.service.TradingOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
@AllArgsConstructor
public class TradingOrderServiceImpl implements TradingOrderService {
  private final TradingOrderRepository tradingOrderRepository;
  private final TradingOrderMapper tradingOrderMapper;

  @Override
  @Transactional
  public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
    TradingOrder tradingOrder = tradingOrderRepository.save(tradingOrderMapper.toTradingOrder(createOrderRequest));
    return tradingOrderMapper.toOrderResponse(tradingOrder);
  }

  @Override
  @Transactional(readOnly = true)
  public OrderResponse getOrder(Long id) {
    return tradingOrderRepository.findById(id)
          .map(tradingOrderMapper::toOrderResponse)
          .orElseThrow(() -> new ResourceNotFoundException("Trading order not found with id: " + id));
  }

  /**
   * https://medium.com/@AlexanderObregon/javas-optional-map-method-explained-0a19206d6704
   *
   * @param id
   * @param updateOrderRequest
   * @return
   */
  @Override
  @Transactional
  public OrderResponse updateOrder(Long id, UpdateOrderRequest updateOrderRequest) {
    return tradingOrderRepository.findById(id)
          .map(existingOrder -> {
            tradingOrderMapper.updateTradingOrderFromRequest(updateOrderRequest, existingOrder);
            return tradingOrderMapper.toOrderResponse(tradingOrderRepository.save(existingOrder));
          })
          .orElseThrow(() -> new ResourceNotFoundException("Trading order not found with id: " + id));
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
    return tradingOrderRepository.findById(id)
          .map(existingOrder -> {
            tradingOrderMapper.updateTradingOrder(updateOrderRequest, existingOrder);
            return tradingOrderMapper.toOrderResponse(tradingOrderRepository.save(existingOrder));
          })
          .orElseThrow(() -> new ResourceNotFoundException("Trading order with id: " + id + " not found"));

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

