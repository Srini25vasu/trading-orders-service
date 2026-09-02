package com.jusmore.trading.platform.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Embeddable
public record UserId(
      @Column(name = "user_id", nullable = false, updatable = false)
      UUID value
) {
  public static UserId create() {
    return new UserId(UUID.randomUUID());
  }
}

