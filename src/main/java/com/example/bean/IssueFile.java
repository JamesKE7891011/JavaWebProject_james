package com.example.bean;

public class IssueFile {
	private Integer issueFileId;
	private String issueFileName;
	
	public IssueFile() {
		
	}
	
	public IssueFile(Integer issueFileId, String issueFileName) {
		super();
		this.issueFileId = issueFileId;
		this.issueFileName = issueFileName;
	}

	public Integer getIssueFileId() {
		return issueFileId;
	}

	public void setIssueFileId(Integer issueFileId) {
		this.issueFileId = issueFileId;
	}

	public String getIssueFileName() {
		return issueFileName;
	}

	public void setIssueFileName(String issueFileName) {
		this.issueFileName = issueFileName;
	}

	@Override
	public String toString() {
		return "IssueFile [issueFileId=" + issueFileId + ", issueFileName=" + issueFileName + "]";
	}
		
}
