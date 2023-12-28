package com.example.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.Employee;
import com.example.bean.ProjectMember;

@Repository("projectmemberdaomysql")
public class ProjectMemberDaoimplMySQL implements ProjectMemberDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addProjectMember(ProjectMember projectMember) {
		String sql = "insert into projectmember(projectId, employeeId) values(?,?)";
		return jdbcTemplate.update(sql, projectMember.getProjectId(), projectMember.getEmployeeId());
	}

	@Override
	public int removeProjectMember(int projectId, int employeeId) {
		String sql = "delete from projectMember where projectId = ? and employeeId = ?";
	    return jdbcTemplate.update(sql, projectId, employeeId);
	}
	
	@Override
	public Optional<ProjectMember> findProjectMemberById(String projectId) {
		String sql = "select projectId,employeeId from projectmember where projectId = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) -> {
				ProjectMember projectMember = new ProjectMember();
				projectMember.setProjectId(rs.getString("projectId"));
				projectMember.setEmployeeId(rs.getInt("employeeId"));
				return projectMember;
			}));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	//為ProjectMember注入employee
	private void enrichProjectMemberWithEmployee(ProjectMember projectMember){
		//注入
		ProjectDaoImplMySQL dao = new ProjectDaoImplMySQL();
		dao.findProjectById(projectMember.getProjectId()).ifPresent(projectMember::setProject);
		
		String sqlItems = "select emplyeeId,employeeName from employee where projectId = ?";
		List<Employee> employees = jdbcTemplate.query(sqlItems,new BeanPropertyRowMapper<>(Employee.class),projectMember.getProject());
		
	}
}


