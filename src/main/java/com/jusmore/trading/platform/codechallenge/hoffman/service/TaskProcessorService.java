package com.jusmore.trading.platform.codechallenge.hoffman.service;

import com.jusmore.trading.platform.codechallenge.hoffman.model.TaskResult;
import com.jusmore.trading.platform.codechallenge.hoffman.model.UserTask;

import java.util.List;

public interface TaskProcessorService {

  /**
   * Verarbeite eine größe Liste von user task asynchron mittels Virtual threads.
   * @param userTasks
   * @return Liste von TaskResult
   */
  List<TaskResult> processBatch(List<UserTask> userTasks);

  /**
   * Verarbeitet einen einzeln user task unter Nutzung von Structured concurrency.(Java 21)
   * @param userTask
   * @return
   */
  TaskResult processSingleUserTask(UserTask userTask);



}
