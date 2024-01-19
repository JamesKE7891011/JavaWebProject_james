package com.example.bean;

public class Schedule {
	
	private Integer scheduleId;
	
	private String projectId;
	
	public Schedule() {
		
	}

	public Schedule(Integer scheduleId, String projectId) {
		super();
		this.scheduleId = scheduleId;
		this.projectId = projectId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", projectId=" + projectId + "]";
	}
	
}
