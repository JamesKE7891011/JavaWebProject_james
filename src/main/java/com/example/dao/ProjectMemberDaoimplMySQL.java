package com.example.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.Employee;
import com.example.bean.ProjectMember;

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

	@Override
	public List<ProjectMember> findAllProjectMembers() {
	    String sql = "SELECT project_id, employee_id FROM project_member";

	    return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
	        // Create a new Employee object
	    	ProjectMember projectMember = new ProjectMember();

	        // Set the attributes of the Employee object
	    	projectMember.setProjectId(rs.getString("project_id"));
	    	projectMember.setEmployeeId(rs.getInt("employee_id"));

	        // Return the Employee object
	        return projectMember;
	    });
	}

}


