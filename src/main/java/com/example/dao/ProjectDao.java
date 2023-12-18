package com.example.dao;

import java.util.Date;
import java.util.List;

import com.example.bean.Project;

public interface ProjectDao {
	
	// 新增專案：
	int addProject(Project project);

	// 取消專案
	int cancelProject(String projectId);

	// 查看專案
	List<Project> findAllProjects();

	// 修改預約人
	int updateProject(String projectId, String newprojectName, String newcontent, String newowner, List<String> newmembers,
			Date newstartDate, Date newendDate);
}
