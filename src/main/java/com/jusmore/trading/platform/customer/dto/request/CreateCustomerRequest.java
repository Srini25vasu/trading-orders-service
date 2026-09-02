package com.jusmore.trading.platform.customer.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(
      @NotNull
      String name,
      @NotNull
      String email,
      @NotNull
      String phone
) {
}

