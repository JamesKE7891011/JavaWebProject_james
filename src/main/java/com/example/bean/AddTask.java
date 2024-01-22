package com.example.bean;

import java.util.Date;

import com.google.gson.Gson;

public class AddTask {

	private String projectId;
	private Integer scheduleId;
	private String taskName;
	private String  taskResource;
	private Date taskStartDate;
	private Date taskEndDate;
	private Integer taskDependency;
	
	public AddTask() {
		
	}
	
	public AddTask(String projectId, Integer scheduleId, String taskName, String taskResource, Date taskStartDate,
			Date taskEndDate, Integer taskDependency) {
		this.projectId = projectId;
		this.scheduleId = scheduleId;
		this.taskName = taskName;
		this.taskResource = taskResource;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
		this.taskDependency = taskDependency;
	}

	public String getProjectId() {
		return projectId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskResource() {
		return taskResource;
	}

	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public Integer getTaskDependency() {
		return taskDependency;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskResource(String taskResource) {
		this.taskResource = taskResource;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public void setTaskDependency(Integer taskDependency) {
		this.taskDependency = taskDependency;
	}
	
	public String toString() {
		return new Gson().toJson(this);
	}
}
