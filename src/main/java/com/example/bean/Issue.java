package com.example.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Issue {
	
	private String projectId;
	
	private Integer issueId;
	
	private String issueName;
	
	private String issueClassId;
			
	private String issueContent;
	
	private List<IssueFile> issueFiles;
	
	private Integer issueStatus;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date issueDateTime;
	
	public Issue() {
		
	}

	public Issue(String projectId, Integer issueId, String issueName, String issueClassId, String issueContent,
			List<IssueFile> issueFiles, Integer issueStatus, Date issueDateTime) {
		this.projectId = projectId;
		this.issueId = issueId;
		this.issueName = issueName;
		this.issueClassId = issueClassId;
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

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getIssueClassId() {
		return issueClassId;
	}

	public void setIssueClassId(String issueClassId) {
		this.issueClassId = issueClassId;
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
		return "Issue [projectId=" + projectId + ", issueId=" + issueId + ", issueName=" + issueName + ", issueClassId="
				+ issueClassId + ", issueContent=" + issueContent + ", issueFiles=" + issueFiles + ", issueStatus="
				+ issueStatus + ", issueDateTime=" + issueDateTime + "]";
	}
	
	
	
}
