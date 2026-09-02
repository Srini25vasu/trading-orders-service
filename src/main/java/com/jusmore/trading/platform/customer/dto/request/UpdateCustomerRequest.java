package com.jusmore.trading.platform.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
      @Size(min = 8, max = 20)
      String name,
      @Email
      String email,
      //@Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
      String phone
) {
}

