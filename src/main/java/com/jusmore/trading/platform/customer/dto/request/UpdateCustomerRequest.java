package com.jusmore.trading.platform.customer.dto.request;

public record UpdateCustomerRequest(
      String name,
      String email,
      String phone
) {
}

