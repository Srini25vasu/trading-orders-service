package com.jusmore.trading.platform.customer.service.impl;

import com.jusmore.trading.platform.customer.domain.Task;
import com.jusmore.trading.platform.customer.domain.TaskId;
import com.jusmore.trading.platform.customer.dto.request.CreateTaskRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateTaskRequest;
import com.jusmore.trading.platform.customer.dto.response.TaskResponse;
import com.jusmore.trading.platform.customer.mapper.TaskMapper;
import com.jusmore.trading.platform.customer.repository.TaskRepository;
import com.jusmore.trading.platform.customer.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Override
  public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
    log.info("Creating task with request: {}", createTaskRequest);
    try {
      Task task = taskMapper.toTask(createTaskRequest);
      if (task.getId() == null) {
        log.warn("Task ID is null, generating a new ID");
        task.setId(TaskId.create());
      }
      log.info("Task erstellete mit ID: {}", task.getId());
      return taskMapper.toTaskResponse(taskRepository.save(task));
    } catch (DataAccessException ex) {
      log.error("Failed to create task", ex);
      throw new RuntimeException("Failed to create task", ex);
    }
  }

  @Override
  public TaskResponse getTaskById(String taskId) {
    log.info("Fetching task with ID: {}", taskId);
    return taskMapper.toTaskResponse(taskRepository
          .findById(TaskId.create(taskId))
          .orElseThrow(() -> {
            log.error("Task not found with ID: {}", taskId);
            return new RuntimeException("Task not found");
          })
    );
  }

  @Override
  public List<TaskResponse> getAllTasks() {
    log.info("Fetching all tasks");
    return taskRepository.findAll()
          .stream().map(taskMapper::toTaskResponse)
          .toList();
  }

  @Override
  public TaskResponse updateTask(String taskId, UpdateTaskRequest updateTaskRequest) {
    log.info("Updating task with ID: {} and request: {}", taskId, updateTaskRequest);
    return taskMapper.toTaskResponse(taskRepository.findById(TaskId.create(taskId)).orElseThrow(() -> new RuntimeException("Task not found")));
  }

  @Override
  public void deleteTask(String taskId) {
    log.info("Deleting task with ID: {}", taskId);

  }
}
