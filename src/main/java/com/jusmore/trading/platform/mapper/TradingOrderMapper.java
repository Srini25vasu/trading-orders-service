package com.jusmore.trading.platform.mapper;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.request.UpdateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
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
