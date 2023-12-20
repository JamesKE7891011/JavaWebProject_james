package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.Project;

@Repository("projectdaomysql")
public class ProjectDaoImplMySQL implements ProjectDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int addProject(Project project) {
		String sql1 = "insert into project(project_id,project_name,project_content,project_owner,project_start,project_end) values(?,?,?,?,?,?)";
		
		return jdbcTemplate.update(
				sql1,
				project.getProjectId(),
				project.getProjectName(),
				project.getContent(),
				project.getOwner(),
				project.getStartDate(),
				project.getEndDate());
	}

	@Override
	public int[] addProjectMember(String projectId,List<String> members) {
		
		String sql = "insert into project_member(project_id, employee_id) values(?,?)";
		
		BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, projectId);
			    ps.setString(2, members.get(i));
			}

			@Override
			public int getBatchSize() {
				return members.size();
			}
			
		};
		
		return jdbcTemplate.batchUpdate(sql, bps);
	}
	
	@Override
	public int cancelProject(String projectId) {
		String sql = "delete from project where project_id = ?";		
		int rowcount = jdbcTemplate.update(sql, projectId);
		return rowcount;
	}

	@Override
	public List<Project> findAllProjects() {
	    String sql = "select project_id,project_name,project_content,project_owner,project_start,project_end from project";
	    return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Project.class));
	}
	


	@Override
	public int updateProject(String projectId, String newprojectName, String newcontent, String newowner,
	        List<String> newmembers, Date newstartDate, Date newendDate) {
	    String sql = "update project set project_name = ?, project_content = ?, project_owner = ?, project_start = ?, project_end = ? where project_id = ?";
	    return jdbcTemplate.update(
	            sql,
	            newprojectName,
	            newcontent,
	            newowner,
	            newstartDate,
	            newendDate,
	            projectId);
	}
}
