package com.jusmore.trading.platform.order.mapper;

import com.jusmore.trading.platform.order.domain.TradingOrder;
import com.jusmore.trading.platform.order.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.order.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.order.dto.response.OrderResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TradingOrderMapper {
  OrderResponse toOrderResponse(TradingOrder tradingOrder);
  TradingOrder toTradingOrder(CreateOrderRequest createOrderRequest);
  void updateTradingOrderFromRequest(UpdateOrderRequest updateOrderRequest, @MappingTarget TradingOrder tradingOrder);
  List<OrderResponse> toOrderResponseList(List<TradingOrder> tradingOrderList);
  @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
  )
  @Mapping(target = "id", ignore = true)
  void updateTradingOrder(UpdateOrderRequest updateOrderRequest, @MappingTarget TradingOrder tradingOrder);
}

