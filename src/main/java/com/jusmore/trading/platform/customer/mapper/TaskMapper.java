package com.jusmore.trading.platform.customer.mapper;

import com.jusmore.trading.platform.customer.domain.Task;
import com.jusmore.trading.platform.customer.dto.request.CreateTaskRequest;
import com.jusmore.trading.platform.customer.dto.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Task toTask(CreateTaskRequest createTaskRequest);

  TaskResponse toTaskResponse(Task task);
}
