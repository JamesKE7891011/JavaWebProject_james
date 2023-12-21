package com.example.bean;

import java.util.List;

public class ProjectMember {
	
	private String projectId;	
	private Integer employeeId;
	
	private Project project;
	private List<Employee> employees;
	
	public ProjectMember() {
		
	}
	
	

	public ProjectMember(String projectId, Integer employeeId, Project project, List<Employee> employees) {
		super();
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.project = project;
		this.employees = employees;
	}

	

	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public Integer getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}



	public Project getProject() {
		return project;
	}



	public void setProject(Project project) {
		this.project = project;
	}



	public List<Employee> getEmployees() {
		return employees;
	}



	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}



	@Override
	public String toString() {
		return "ProjectMember [projectId=" + projectId + ", employeeId=" + employeeId + "]";
	} 
	
	
}
