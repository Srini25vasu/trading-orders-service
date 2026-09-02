package com.jusmore.trading.platform.customer.mapper;

import com.jusmore.trading.platform.customer.domain.Payment;
import com.jusmore.trading.platform.customer.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  @Mapping(target = "cvv", constant = "[REDACTED]")
  @Mapping(target = "creditCardNumber", expression = "java(maskCard(entity.getCreditCardNumber()))")
  PaymentDto toDto(Payment entity);

  // Custom logic to mask a credit card number
  default String maskCard(String cardNumber) {
    if (cardNumber == null || cardNumber.length() < 4) return "****";
    return "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
  }
}
