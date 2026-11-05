package com.jusmore.trading.platform.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record TaskId(
      @Column(name = "task_id", nullable = false, updatable = false)
      UUID taskId
) {
  public static TaskId create() {
    return new TaskId( UUID.randomUUID());
  }
  public static TaskId create(String id) {
    return new TaskId(UUID.fromString(id));
  }
}
