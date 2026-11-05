package com.jusmore.trading.platform.customer.controller;

import com.jusmore.trading.platform.customer.domain.Task;
import com.jusmore.trading.platform.customer.dto.request.CreateTaskRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateTaskRequest;
import com.jusmore.trading.platform.customer.dto.response.TaskResponse;
import com.jusmore.trading.platform.customer.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest taskRequest) {
    return taskService.createTask(taskRequest);
  }
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TaskResponse> getAllTasks() {
    return taskService.getAllTasks();
  }
  @GetMapping("/{taskId}")
  @ResponseStatus(HttpStatus.OK)
  public TaskResponse getTaskById(@PathVariable String taskId) {
    return taskService.getTaskById(taskId);
  }
  @PutMapping("/{taskId}")
  @ResponseStatus(HttpStatus.OK)
  public TaskResponse updateTask(@PathVariable String taskId, @Valid @RequestBody UpdateTaskRequest taskRequest) {
    return taskService.updateTask(taskId, taskRequest);
  }
}
