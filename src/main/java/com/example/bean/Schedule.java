package com.example.bean;

import java.util.List;

import com.google.gson.Gson;

public class Schedule {
	
	private Integer scheduleId;
	
	private String projectId;
	
	private List<Task> tasks;
	
	public Schedule() {
		
	}

	public Schedule(Integer scheduleId, String projectId, List<Task> tasks) {
		super();
		this.scheduleId = scheduleId;
		this.projectId = projectId;
		this.tasks = tasks;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	
	
}
