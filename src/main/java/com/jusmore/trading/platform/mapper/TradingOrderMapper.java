package com.jusmore.trading.platform.mapper;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.request.CreateOrderRequest;
import com.jusmore.trading.platform.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TradingOrderMapper {
  OrderResponse toOrderResponse(TradingOrder tradingOrder);
  TradingOrder toTradingOrder(CreateOrderRequest createOrderRequest);
}
