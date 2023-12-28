package com.example.bean;

import com.google.gson.Gson;

public class ProjectMember {
	
	private String projectId;	
	
	private Integer employeeId;

	public ProjectMember() {
		
	}
	
	public ProjectMember(String projectId, Integer employeeId) {
		this.projectId = projectId;
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
