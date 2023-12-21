package com.example.dao;

import java.util.List;

import com.example.bean.Employee;
import com.example.bean.ProjectMember;

public interface ProjectMemberDao {

	// 增加專案成員
	int addProjectMember(int projectId, int employeeId);
	
	//移除專案成員
	int removeProjectMember(int projectId, int employeeId);
	
	//查詢所有專案成員
	List<Employee> findAllProjectMembers();
	
}
