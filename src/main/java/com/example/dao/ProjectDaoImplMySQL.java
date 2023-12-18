package com.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.Project;

@Repository("ProjectDaoMySQL")
public class ProjectDaoImplMySQL implements ProjectDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int addProject(Project project) {
		String sql = "insert into project(project_id,project_name,project_content,project_owner,project_start,project_end) values(?,?,?,?,?,?)";
		String sql1 = "insert into project_member(employee_id) values(?)";
		int rowcount = jdbcTemplate.update(sql,project.getProjectId(),project.getProjectName(),project.getContent(),project.getOwner(),project.getStartDate(),project.getEndDate());
		int rowcount1 = jdbcTemplate.update(sql1,project.getMembers());
		return rowcount + rowcount1;
	}

	@Override
	public int cancelProject(String projectId) {
		String sql = "delete from project where project_id = ?";		
		int rowcount = jdbcTemplate.update(sql, projectId);
		return rowcount;
	}

	@Override
	public List<Project> findAllProjects() {

		return null;
	}

	@Override
	public int updateProject(String projectId, String newprojectName, String newcontent, String newowner,
			List<String> newmembers, Date newstartDate, Date newendDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
