package com.jusmore.trading.platform.idempotency.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record IdempotencyKeyDto(
      @NotNull(message = "Idempotency key cannot be null")
      @Length(min = 10, max = 100, message = "Idempotency key must be between 10 and 100 characters")
      String idempotencyKey,
      @NotNull(message = "User ID cannot be null")
      @Length(min = 10, max = 100, message = "User ID must be between 10 and 100 characters")
      String userId
) {
}

