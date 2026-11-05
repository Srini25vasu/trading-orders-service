package com.jusmore.trading.platform.customer.service;

import com.jusmore.trading.platform.customer.domain.Task;
import com.jusmore.trading.platform.customer.dto.request.CreateTaskRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateTaskRequest;
import com.jusmore.trading.platform.customer.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
  public TaskResponse createTask(CreateTaskRequest createTaskRequest);
  public TaskResponse getTaskById(String taskId);
  public List<TaskResponse> getAllTasks();
  public TaskResponse updateTask(String taskId, UpdateTaskRequest updateTaskRequest);
  public void deleteTask(String taskId);
}
