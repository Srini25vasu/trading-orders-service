package com.jusmore.trading.platform.service.impl;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.mapper.TradingOrderMapper;
import com.jusmore.trading.platform.repository.entities.TradingOrderRepository;
import com.jusmore.trading.platform.service.TradingOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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
        .orElse(null);
  }

  @Override
  @Transactional
  public OrderResponse updateOrder(Long id, CreateOrderRequest createOrderRequest) {
    return tradingOrderRepository.findById(id)
        .map(existingOrder -> {
          TradingOrder updatedOrder = tradingOrderMapper.toTradingOrder(createOrderRequest);
          updatedOrder.setId(existingOrder.getId());
          return tradingOrderMapper.toOrderResponse(tradingOrderRepository.save(updatedOrder));
        })
        .orElse(null);
  }

  @Override
  public void deleteOrder(Long id) {
    tradingOrderRepository.deleteById(id);
  }

  @Override
  public List<OrderResponse> getAllOrders() {
    return tradingOrderRepository.findAll().stream()
        .map(tradingOrderMapper::toOrderResponse)
        .toList();
  }
}
