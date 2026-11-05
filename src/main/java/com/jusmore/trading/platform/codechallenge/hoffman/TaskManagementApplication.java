package com.jusmore.trading.platform.codechallenge.hoffman;

import com.jusmore.trading.platform.codechallenge.hoffman.model.TaskResult;
import com.jusmore.trading.platform.codechallenge.hoffman.model.TaskType;
import com.jusmore.trading.platform.codechallenge.hoffman.model.User;
import com.jusmore.trading.platform.codechallenge.hoffman.model.UserTask;
import com.jusmore.trading.platform.codechallenge.hoffman.service.TaskProcessorService;
import com.jusmore.trading.platform.codechallenge.hoffman.service.impl.TaskProcessorServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * https://gemini.google.com/app/526a6e84145feade
 */

public class TaskManagementApplication {
  public static void main(String[] args) {
    TaskProcessorService taskProcessorService = new TaskProcessorServiceImpl();
    List<UserTask> userTasks = List.of(
          new UserTask(UUID.randomUUID(),  null, null, Instant.now(), Instant.now())
    );
    List<TaskResult> taskResults = taskProcessorService.processBatch(userTasks);
    taskResults.forEach(taskResult -> {
      System.out.println(taskResult);
    });
  }
}
