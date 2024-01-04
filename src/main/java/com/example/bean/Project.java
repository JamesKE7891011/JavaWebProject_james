package com.example.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

public class Project {
	
	private String projectId;
	
	private String projectName;
	
	private String projectContent;
	
	private Employee projectOwner;
	
	private List<Employee> projectMembers;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date projectStartDate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date projectEndDate;
	
	
	public Project() {
		
	}
	
	
	public Project(String projectId, String projectName, String projectContent, Employee projectOwner,
			List<Employee> projectMembers, Date projectStartDate, Date projectEndDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectContent = projectContent;
		this.projectOwner = projectOwner;
		this.projectMembers = projectMembers;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
	}

	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectContent() {
		return projectContent;
	}


	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}


	public Employee getProjectOwner() {
		return projectOwner;
	}


	public void setProjectOwner(Employee projectOwner) {
		this.projectOwner = projectOwner;
	}


	public List<Employee> getProjectMembers() {
		return projectMembers;
	}


	public void setProjectMembers(List<Employee> projectMembers) {
		this.projectMembers = projectMembers;
	}


	public Date getProjectStartDate() {
		return projectStartDate;
	}


	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}


	public Date getProjectEndDate() {
		return projectEndDate;
	}


	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
