package com.jusmore.trading.platform.customer.dto.response;

import java.util.List;

public record CustomerResponse(
      String name,
      String email,
      String phone,
      List<CustomerOrderResponse> customerOrders
) {
}
