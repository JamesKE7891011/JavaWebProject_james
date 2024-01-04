package com.example.bean;

public class IssueFile {
	private Integer issueFileId;
	private Integer issueId;
	private String issueFileName;
	
	public IssueFile() {
		
	}

	public IssueFile(Integer issueFileId, Integer issueId, String issueFileName) {
		super();
		this.issueFileId = issueFileId;
		this.issueId = issueId;
		this.issueFileName = issueFileName;
	}

	public Integer getIssueFileId() {
		return issueFileId;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public String getIssueFileName() {
		return issueFileName;
	}

	public void setIssueFileId(Integer issueFileId) {
		this.issueFileId = issueFileId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public void setIssueFileName(String issueFileName) {
		this.issueFileName = issueFileName;
	}

	@Override
	public String toString() {
		return "IssueFile [issueFileId=" + issueFileId + ", issueId=" + issueId + ", issueFileName=" + issueFileName
				+ "]";
	}
		
}
