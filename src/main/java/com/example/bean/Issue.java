package com.example.bean;

import java.sql.Date;

public class Issue {
	private String projectId;
	private Integer issueId;
	private String issueClass;
	private String issueName;
	private String issueContent;
	private Integer issueStatus;
	private  Date issueDateTime;
	
	public Issue() {
		
	}

	public Issue(String projectId, Integer issueId, String issueClass, String issueName, String issueContent,
			Integer issueStatus, Date issueDateTime) {
		super();
		this.projectId = projectId;
		this.issueId = issueId;
		this.issueClass = issueClass;
		this.issueName = issueName;
		this.issueContent = issueContent;
		this.issueStatus = issueStatus;
		this.issueDateTime = issueDateTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public String getIssueClass() {
		return issueClass;
	}

	public String getIssueName() {
		return issueName;
	}

	public String getIssueContent() {
		return issueContent;
	}

	public Integer getIssueStatus() {
		return issueStatus;
	}

	public Date getIssueDateTime() {
		return issueDateTime;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public void setIssueClass(String issueClass) {
		this.issueClass = issueClass;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public void setIssueDateTime(Date issueDateTime) {
		this.issueDateTime = issueDateTime;
	}

	@Override
	public String toString() {
		return "Issue [projectId=" + projectId + ", issueId=" + issueId + ", issueClass=" + issueClass + ", issueName="
				+ issueName + ", issueContent=" + issueContent + ", issueStatus=" + issueStatus + ", issueDateTime="
				+ issueDateTime + "]";
	}
	
}
