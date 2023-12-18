package com.example.bean;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class Project {
	
	private String projectId;
	
	private String projectName;
	
	private String content;
	
	private String owner;
	
	private List<String> members;
	
	private Date startDate;
	
	private Date endDate;
	
	
	public Project() {
		
	}
	
	public Project(String projectId, String projectName, String content, String owner, List<String> members,
			Date startDate, Date endDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.content = content;
		this.owner = owner;
		this.members = members;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getContent() {
		return content;
	}

	public String getOwner() {
		return owner;
	}

	public List<String> getMembers() {
		return members;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
