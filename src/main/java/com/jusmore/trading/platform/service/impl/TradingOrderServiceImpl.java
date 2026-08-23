package com.jusmore.trading.platform.service.impl;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import com.jusmore.trading.platform.exception.ResourceNotFoundException;
import com.jusmore.trading.platform.mapper.TradingOrderMapper;
import com.jusmore.trading.platform.repository.entities.TradingOrderRepository;
import com.jusmore.trading.platform.service.TradingOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    if (!tradingOrderRepository.existsById(id)) {
      throw new ResourceNotFoundException("Trading order not found with id: " + id);
    }
    tradingOrderRepository.deleteById(id);
  }

  @Override
  public List<OrderResponse> getAllOrders() {
    return tradingOrderMapper.toOrderResponseList(tradingOrderRepository.findAll());
  }
}
