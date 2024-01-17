package com.example.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import com.example.bean.Project;

public interface ProjectDao {
	
	// 新增專案：
	int addProject(Project project) throws SQLIntegrityConstraintViolationException;

	// 根據專案ID刪除指定的專案
	int removeProjectById(String projectId);
	
	// 查詢專案(多筆)
	List<Project> findAllProjects();
	
	// 根據專案ID查找專案(單筆)
	Optional<Project> findProjectById(String projectId);
	
	// 更新專案內容
	int updateProject(Project projectUpdate);

	

	
}
