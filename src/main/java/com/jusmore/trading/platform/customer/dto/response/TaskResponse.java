package com.jusmore.trading.platform.customer.dto.response;

import com.jusmore.trading.platform.customer.enums.TaskStatus;

public record TaskResponse(
      String title,
      String description,
      TaskStatus status
) {
}
