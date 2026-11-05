package com.jusmore.trading.platform.customer.repository;

import com.jusmore.trading.platform.customer.domain.Task;
import com.jusmore.trading.platform.customer.domain.TaskId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, TaskId> {
}
