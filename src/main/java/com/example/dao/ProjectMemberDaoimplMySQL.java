package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.ProjectMember;

@Repository("projectmemberdaomysql")
public class ProjectMemberDaoimplMySQL implements ProjectMemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addProjectMember(ProjectMember projectMember) {
		String sql = "insert into projectmember(projectId, employeeId) values(?,?)";
		return jdbcTemplate.update(sql, projectMember.getProjectId(), projectMember.getEmployeeId());
	}

	@Override
    public int removeProjectMember(String projectId) {
        String sql = "delete from projectMember where projectId = ?";
        return jdbcTemplate.update(sql,projectId);
	}

	@Override
	public List<ProjectMember> findProjectMemberById(String projectId) {
		String sql = "select projectId,employeeId from projectmember where projectId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectMember.class), projectId);
	}
}
