package com.jusmore.trading.platform.codechallenge.hoffman.model;

import java.util.UUID;

public record TaskResult(
      UUID taskId,
      TaskStatus taskType,
      User user,
      String result,
      long durationMs
) {
}
