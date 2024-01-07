package com.example.bean;

import java.sql.Date;
import java.util.List;

public class Issue {
	
	private String projectId;
	
	private Integer issueId;
	
	private String issueClass;
	
	private String issueName;
	
	private String issueContent;
	
	private List<IssueFile> issueFiles;
	
	private Integer issueStatus;
	
	private  Date issueDateTime;
	
	
	
	public Issue() {
		
	}

	public Issue(String projectId, Integer issueId, String issueClass, String issueName, String issueContent,
			List<IssueFile> issueFiles, Integer issueStatus, Date issueDateTime) {
		this.projectId = projectId;
		this.issueId = issueId;
		this.issueClass = issueClass;
		this.issueName = issueName;
		this.issueContent = issueContent;
		this.issueFiles = issueFiles;
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

	public List<IssueFile> getIssueFiles() {
		return issueFiles;
	}

	public void setIssueFiles(List<IssueFile> issueFiles) {
		this.issueFiles = issueFiles;
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
				+ issueName + ", issueContent=" + issueContent + ", issueFiles=" + issueFiles + ", issueStatus="
				+ issueStatus + ", issueDateTime=" + issueDateTime + "]";
	}
	
	

	
	
}
