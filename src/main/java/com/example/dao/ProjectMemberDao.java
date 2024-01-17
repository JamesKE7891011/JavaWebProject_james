package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Employee;
import com.example.bean.Project;
import com.example.bean.ProjectMember;
import com.google.protobuf.Option;

public interface ProjectMemberDao {

	// 新增專案成員
	int[] addProjectMember(String projectId,List<Integer> members);
	
	// 根據專案ID刪除該專案成員
	int removeProjectMember(String projectId);
	
	// 查詢專案ID查詢多名專案成員(多筆)
	List<ProjectMember> findProjectMemberById(String projectId);
	
	
}
