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
	@Transactional(propagation = Propagation.REQUIRED)
	public int[] addProjectMember(String projectId, List<Integer> projectMembers) {

		String sql = "insert into projectmember(projectId, employeeId) values(?,?)";

		BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, projectId);
				ps.setInt(2, projectMembers.get(i));
			}

			@Override
			public int getBatchSize() {
				return projectMembers.size();
			}

		};

		return jdbcTemplate.batchUpdate(sql, bps);
	}

	@Override
	public int removeprojectById(String projectId) {
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

	// 為 projectMember 注入 employee
	// details: 專案(project) 與 員工(employees)資料
	private void enrichProjectMemberWithEmployee(Project project) {

		// 設定 Project Member

		// 設定 Member Employee

	}
}
