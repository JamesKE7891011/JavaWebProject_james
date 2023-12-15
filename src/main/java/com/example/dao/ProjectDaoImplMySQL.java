package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.bean.Project;

public class ProjectDaoImplMySQL implements ProjectDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int addProject(Project project) {

		return 0;
	}

	@Override
	public int cancelProject(String projectId) {

		return 0;
	}

	@Override
	public List<Project> findAllProjects() {

		return null;
	}

	@Override
	public int updateProject(Project project) {

		return 0;
	}

}
