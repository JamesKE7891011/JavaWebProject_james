package com.example.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

public class Project {
	
	private String projectId;
	
	private String projectName;
	
	private String projectContent;
	
	private Employee projectOwner;
	
	private List<Employee> projectMembers;
	
	@Temporal(TemporalType.DATE)                            // 指定日期型態的屬性
	@DateTimeFormat(pattern = "yyyy-MM-dd")                 // 指定日期型態的格式("年-月-日" )
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")    // pattern 希望將日期格式化為 "年-月-日" 的形式。
	private Date projectStartDate;                          // timeZone 指定時區為 GMT+8，以確保日期時間的正確轉換。
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date projectEndDate;
		
	public Project() {
		
	}
		
	public Project(String projectId, String projectName, String projectContent, Employee projectOwner,
			List<Employee> projectMembers, Date projectStartDate, Date projectEndDate) {
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

	public Employee getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(Employee projectOwner) {
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
