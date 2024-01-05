package com.example.bean;

public class IssueFile {
	private Integer issueFileId;
	private Integer issueId;
	private String issueFilePath;
	
	public IssueFile() {
		
	}

	public IssueFile(Integer issueFileId, Integer issueId, String issueFilePath) {
		super();
		this.issueFileId = issueFileId;
		this.issueId = issueId;
		this.issueFilePath = issueFilePath;
	}

	public Integer getIssueFileId() {
		return issueFileId;
	}

	public void setIssueFileId(Integer issueFileId) {
		this.issueFileId = issueFileId;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getIssueFilePath() {
		return issueFilePath;
	}

	public void setIssueFilePath(String issueFilePath) {
		this.issueFilePath = issueFilePath;
	}

	@Override
	public String toString() {
		return "IssueFile [issueFileId=" + issueFileId + ", issueId=" + issueId + ", issueFilePath=" + issueFilePath
				+ "]";
	}
	
}
