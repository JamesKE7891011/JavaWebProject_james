package com.example.dao;

public interface ProjectMemberDao {

	int addProjectMember(int projectId, int employeeId);
	
	int removeProjectMember(int projectId, int employeeId);
}
