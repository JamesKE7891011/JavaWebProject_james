package com.example.bean;

import java.util.List;

import com.google.gson.Gson;

public class Employee {

	private Integer employeeId;	
	private String projectId;	
	private String employeeName;	
		
	private ProjectMember projectMember;
	
	public Employee() {
		
	}


	public Employee(Integer employeeId, String projectId, String employeeName, ProjectMember projectMember) {
		super();
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.employeeName = employeeName;
		this.projectMember = projectMember;
	}
	

	public Integer getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}



	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public String getEmployeeName() {
		return employeeName;
	}



	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



	public ProjectMember getProjectMember() {
		return projectMember;
	}



	public void setProjectMember(ProjectMember projectMember) {
		this.projectMember = projectMember;
	}



	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
