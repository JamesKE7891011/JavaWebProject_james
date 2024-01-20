package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Task;

public interface TaskDao {
	
	int addTask(Task task);
	
	int removeTask(Integer taskId);
	
	List<Task> findAllTasks();
	
	Optional<Task> findTaskByTaskId(Integer taskId);

	List<Task> findTasksByScheduleId(int scheduleId);


}
