package com.jusmore.trading.platform.codechallenge.hoffman.model;

public sealed interface TaskType permits TaskType.EmailNotification, TaskType.DataExport, TaskType.SystemCleanup  {
  record EmailNotification(String subject, String body) implements TaskType {}
  record DataExport(String format) implements TaskType {}
  record SystemCleanup(int daysToKeep) implements TaskType {}
}
