package com.jusmore.trading.platform.customer.dto.request;

import com.jusmore.trading.platform.customer.enums.TaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskRequest(
      @NotNull
      @NotEmpty
      String title,
      @NotNull
      @Max(value = 500, message = "Description must not exceed 500 characters")
      String description,
      @NotNull
      TaskStatus status
) {
}
