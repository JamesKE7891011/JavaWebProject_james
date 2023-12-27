package com.example.bean;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class Project {
	
	private String projectId;
	
	private String projectName;
	
	private String projectContent;
	
	private String projectOwner;
	
	private List<Employee> projectMembers;
	
	private Date projectStartDate;
	
	private Date projectEndDate;
	
	
	public Project() {
		
	}
	
	
	public Project(String projectId, String projectName, String projectContent, String projectOwner,
			List<Employee> projectMembers, Date projectStartDate, Date projectEndDate) {
		super();
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


	public String getProjectOwner() {
		return projectOwner;
	}


	public void setProjectOwner(String projectOwner) {
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
