package com.example.bean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

public class Task {
	
	private Integer taskId;
	
	private Integer scheduleId;
	
	private String taskName;
	
	private String taskResource;
	
	@Temporal(TemporalType.DATE)                            
	@DateTimeFormat(pattern = "yyyy-MM-dd")                 
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date taskStartDate;
	
	@Temporal(TemporalType.DATE)                            
	@DateTimeFormat(pattern = "yyyy-MM-dd")                 
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date taskEndDate;
	
	private int taskDuration;
	
	private Double taskPercentComplete;
	
	private Integer taskDependency;
	
	public Task() {
		
	}

	public Task(Integer taskId, Integer scheduleId, String taskName, String taskResource, Date taskStartDate,
			Date taskEndDate, int taskDuration, Double taskPercentComplete, Integer taskDependency) {
		this.taskId = taskId;
		this.scheduleId = scheduleId;
		this.taskName = taskName;
		this.taskResource = taskResource;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
		this.taskDuration = taskDuration;
		this.taskPercentComplete = taskPercentComplete;
		this.taskDependency = taskDependency;
	}

	public Integer getTaskId() {
		return taskId;
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

	public int getTaskDuration() {
		return taskDuration;
	}

	public Double getTaskPercentComplete() {
		return taskPercentComplete;
	}

	public Integer getTaskDependency() {
		return taskDependency;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public void setTaskDuration(int taskDuration) {
		this.taskDuration = taskDuration;
	}
	
	public void calculateTaskDuration() {
	    if (taskStartDate != null && taskEndDate != null) {
	        LocalDate startDate = taskStartDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
	        LocalDate endDate = taskEndDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

	        // 使用 ChronoUnit.DAYS.between 計算天數差
	        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
	        
	        // 將天數差設置到 taskDuration 屬性
	        taskDuration = (int) daysBetween; // 如果您確定天數差不會超過 int 範圍，可以進行強制轉換
	    } else {
	        taskDuration = 0;
	    }
	}

	public void setTaskPercentComplete(Double taskPercentComplete) {
		this.taskPercentComplete = taskPercentComplete;
	}
	
	public void calculateTaskPercentComplete() {
        if (taskStartDate != null && taskDuration > 0) {
            LocalDate startDate = taskStartDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();

            // 計算已經過的天數
            long daysPassed = ChronoUnit.DAYS.between(startDate, currentDate);

            // 計算完成百分比
            double percentComplete = (double) daysPassed / taskDuration * 100;

            // 限制完成百分比在 0 到 100 之間
            taskPercentComplete = Math.min(100, Math.max(0, percentComplete));
        } else {
            taskPercentComplete = 0.0;
        }
        
        taskPercentComplete = Math.round(taskPercentComplete * 100.0) / 100.0;
    }

	public void setTaskDependency(Integer taskDependency) {
		this.taskDependency = taskDependency;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
