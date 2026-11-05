package com.jusmore.trading.platform.codechallenge.hoffman.service.impl;

import com.jusmore.trading.platform.codechallenge.hoffman.model.*;
import com.jusmore.trading.platform.codechallenge.hoffman.service.TaskProcessorService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TaskProcessorServiceImpl implements TaskProcessorService {

  /**
   * Verarbeitet einen einzeln user task unter Nutzung von Structured concurrency.(Java 21)
   * @param userTasks
   * @return Liste von TaskResult
   */

  @Override
  public List<TaskResult> processBatch(List<UserTask> userTasks) {
    long startTime = System.currentTimeMillis();
    try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      var futures = userTasks.stream()
            .map((userTask) -> executor.submit(() -> processSingleUserTask(userTask)))
            .toList();
      return futures.stream()
            .map(future -> {
              try {
                return future.get();
              } catch (Exception e) {
                log.error("Error processing user Task id: {}",null, e);
                return new TaskResult(UUID.randomUUID(), TaskStatus.FAILED, null, "Error while processing user task",System.currentTimeMillis() - startTime);
              }
            })
            .toList();
    }
  }

  @Override
  public TaskResult processSingleUserTask(UserTask userTask) {
    long startTime = System.currentTimeMillis();
    try {
      /*if (userTask == null || userTask.taskId() == null || userTask.taskType() == null || !validateUser(userTask.user())) {
        log.warn("Invalid user task supplied: {}", userTask);
        return new TaskResult(
              userTask != null ? userTask.taskId() : null,
              TaskStatus.FAILED,
              userTask != null ? userTask.user() : null,
              "Invalid user task",
              System.currentTimeMillis() - startTime
        );
      }*/

      String result = executeTaskLogic(userTask);
      return new TaskResult(
            userTask.taskId(),
            TaskStatus.COMPLETED,
            userTask.user(),
            result,
            System.currentTimeMillis() - startTime
      );
    } catch (Exception e) {
      log.error("Error processing user Task id: {}", userTask != null ? userTask.taskId() : null, e);
      return new TaskResult(
            userTask != null ? userTask.taskId() : null,
            TaskStatus.FAILED,
            userTask != null ? userTask.user() : null,
            "Error while processing user task",
            System.currentTimeMillis() - startTime
      );
    }
  }

  private String executeTaskLogic(UserTask userTask) throws InterruptedException {
    //Simulierte I/O Operation (z.B. Datenbankabfrage). Vitual threads hier wird geparkt, verbraucht aber fast keinen RAM/CPU
    Thread.sleep(1000);
    TaskType taskType = userTask.taskType();
    if (taskType instanceof TaskType.EmailNotification) {
      return "Email sent";
    }
    if (taskType instanceof TaskType.DataExport) {
      return "Data exported";
    }
    if (taskType instanceof TaskType.SystemCleanup) {
      return "System cleaned up";
    }
    //throw new IllegalArgumentException("Unsupported task type: " + taskType);
    return "Task not started because TaskType is null";
  }

  private boolean validateUser(User user){
    return user != null &&
          user.id() != null &&
          user.email() != null &&
          !user.email().isBlank();
  }
}
