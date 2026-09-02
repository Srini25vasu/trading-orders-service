package com.jusmore.trading.platform.customer.mapper;

import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.dto.CustomerDto;
import com.jusmore.trading.platform.customer.dto.request.CreateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.response.CustomerListResponse;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;
import com.jusmore.trading.platform.order.mapper.TradingOrderMapper;
import org.mapstruct.*;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TradingOrderMapper.class)
public interface CustomerMapper {
  Customer toCustomer(CreateCustomerRequest request);

  @Mapping(source = "tradingOrders", target = "customerOrders")
  @Mapping(target = "email", source = "email", qualifiedByName = "maskEmail")
  CustomerResponse toCustomerResponse(Customer customer);

  @Mapping(target = "email", source = "email", qualifiedByName = "maskEmail")
  CustomerListResponse toCustomerListResponse(Customer customer);

  @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
  )
  @Mapping(target = "id", ignore = true)
  void updateCustomerFromRequest(UpdateCustomerRequest request, @MappingTarget Customer customer);

  @Mapping(target = "email", source = "email", qualifiedByName = "maskEmail")
  CustomerDto toDto(CreateCustomerRequest request);

  // Mask only for log DTO mappings that explicitly opt in.
  @Named("maskEmail")
  default String maskEmail(String email) {
    if (email == null || email.isBlank()) {
      return "***";
    }
    int at = email.indexOf('@');
    if (at <= 1) {
      return "***";
    }
    String localPart = email.substring(0, at);
    String domainPart = email.substring(at);
    return localPart.charAt(0) + "***" + domainPart;
  }
}
