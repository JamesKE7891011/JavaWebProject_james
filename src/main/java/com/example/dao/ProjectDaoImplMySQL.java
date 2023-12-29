package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Project;

@Repository("projectdaomysql")
public class ProjectDaoImplMySQL implements ProjectDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addProject(Project project) {
		String sql = "insert into project(projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate) values(?,?,?,?,?,?)";

		return jdbcTemplate.update(sql, project.getProjectId(), project.getProjectName(), project.getProjectContent(),
				project.getProjectOwner(), project.getProjectStartDate(), project.getProjectEndDate());
	}

	

	@Override
	public int removeProjectById(String projectId) {
		String sql = "delete from project where projectId = ? ";
		return jdbcTemplate.update(sql, projectId);
	}

	@Override
	public List<Project> findAllProjects() {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Project.class));
	}

	@Override
	public Optional<Project> findProjectById(String projectId) {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project where projectId";
		try {
			Project project = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Project.class), projectId);
			return Optional.ofNullable(project);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public int updateProject(Project projectUpdate) {
		String sql = "update project set projectName = ?, projectContent = ?, projectOwner = ?, projectStartDate = ?, projectEndDate = ? where projectId = ?";
		return jdbcTemplate.update(sql, projectUpdate.getProjectName(), projectUpdate.getProjectContent(),
				projectUpdate.getProjectOwner(), projectUpdate.getProjectStartDate(), projectUpdate.getProjectEndDate(),
				projectUpdate.getProjectId());
	}


}
