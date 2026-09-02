package com.jusmore.trading.platform.shared_kernel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.util.UUID;

@Embeddable
public record CustomerId(
      @Column(name = "customer_id", nullable = false, updatable = false)
      UUID customerId
) {
  public static CustomerId create() {
    return new CustomerId(UUID.randomUUID());
  }
  public static CustomerId create(String id) {
    return new CustomerId(UUID.fromString(id));
  }
}
