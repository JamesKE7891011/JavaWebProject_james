package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Schedule;

public interface ScheduleDao {
	
	int addSchedule(Schedule schedule);
	
	int removeScheduleByScheduleId(Integer scheduleId);
	
	List<Schedule> findAllSchedules();
	
	Optional<Schedule> findScheduleByScheduleId(Integer scheduleId);
	
	Optional<Schedule> findSchedulueByProjectId(String projectId);
	
}
