package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Project;

public interface ProjectDao {
	
	// 新增專案：
	int addProject(Project project);
	
	// 新增專案成員
	int[] addProjectMember(String projectId,List<String> members);

	// 根據專案ID刪除指定的專案
	int removeprojectById(String projectId);
	
	// 查詢專案(多筆)
	List<Project> findAllProjects();
	
	//根據專案ID查找專案(單筆)
	Optional<Project> findProjectById(String projectId);
	
	// 修改專案內容
	int updateProject(Project projectUpdate);

	
}
