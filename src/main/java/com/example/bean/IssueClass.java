package com.example.bean;

public class IssueClass {
	private String issueClassId;
	private String issueClassName;
	
	public IssueClass() {
		
	}
	
	public IssueClass(String issueClassId, String issueClassName) {
		this.issueClassId = issueClassId;
		this.issueClassName = issueClassName;
	}

	public String getIssueClassId() {
		return issueClassId;
	}

	public void setIssueClassId(String issueClassId) {
		this.issueClassId = issueClassId;
	}

	public String getIssueClassName() {
		return issueClassName;
	}

	public void setIssueClassName(String issueClassName) {
		this.issueClassName = issueClassName;
	}

	@Override
	public String toString() {
		return "IssueClass [issueClassId=" + issueClassId + ", issueClassName=" + issueClassName + "]";
	}
	
	
	
}
