package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("projectmemberdaomysql")
public class ProjectMemberDaoimplMySQL implements ProjectMemberDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public int addProjectMember(int projectId, int employeeId) {
		String sql = "insert into project_member(project_id, employee_id) values(?,?)";
		return jdbcTemplate.update(sql, projectId, employeeId);
	}

	@Override
	public int removeProjectMember(int projectId, int employeeId) {
		String sql = "delete from project_member where project_id = ? and employee_id = ?";
	    return jdbcTemplate.update(sql, projectId, employeeId);
	}
	
	
	
}
