package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Employee;
import com.example.bean.Project;
import com.example.bean.ProjectMember;
import com.google.protobuf.Option;

public interface ProjectMemberDao {

	// 增加專案成員
	int addProjectMember(ProjectMember projectMember);
	
	// 移除專案成員
	int removeProjectMember(String projectId);
	
	
	// 根據Project Id 去找 ProjectMember
	List<ProjectMember> findProjectMemberById(String projectId);
	
	
}
