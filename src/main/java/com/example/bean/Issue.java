package com.example.bean;

import java.sql.Date;

public class Issue {
	private String projectId;
	private Integer issueId;
	private String issueClass;
	private String issueName;
	private String issueContent;
	private Integer issueFileId;
	private Integer issueStatus;
	private  Date issueDateTime;
	
	public Issue() {
		
	}
	
	public Issue(String projectId, Integer issueId, String issueClass, String issueName, String issueContent,
			Integer issueFileId, Integer issueStatus, Date issueDateTime) {
		super();
		this.projectId = projectId;
		this.issueId = issueId;
		this.issueClass = issueClass;
		this.issueName = issueName;
		this.issueContent = issueContent;
		this.issueFileId = issueFileId;
		this.issueStatus = issueStatus;
		this.issueDateTime = issueDateTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getIssueClass() {
		return issueClass;
	}

	public void setIssueClass(String issueClass) {
		this.issueClass = issueClass;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getIssueContent() {
		return issueContent;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	public Integer getIssueFileId() {
		return issueFileId;
	}

	public void setIssueFileId(Integer issueFileId) {
		this.issueFileId = issueFileId;
	}

	public Integer getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Date getIssueDateTime() {
		return issueDateTime;
	}

	public void setIssueDateTime(Date issueDateTime) {
		this.issueDateTime = issueDateTime;
	}

	@Override
	public String toString() {
		return "Issue [projectId=" + projectId + ", issueId=" + issueId + ", issueClass=" + issueClass + ", issueName="
				+ issueName + ", issueContent=" + issueContent + ", issueFileId=" + issueFileId + ", issueStatus="
				+ issueStatus + ", issueDateTime=" + issueDateTime + "]";
	}
		
}
