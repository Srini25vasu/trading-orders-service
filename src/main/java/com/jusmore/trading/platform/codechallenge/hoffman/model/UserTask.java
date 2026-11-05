package com.jusmore.trading.platform.codechallenge.hoffman.model;

import java.time.Instant;
import java.util.UUID;

public record UserTask(
      UUID taskId,
      User user,
      TaskType taskType,
      Instant createdAt,
      Instant completedAt
) {
}
