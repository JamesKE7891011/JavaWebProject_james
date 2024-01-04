package com.example.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Employee;
import com.example.bean.Project;
import com.example.bean.ProjectMember;

@Repository("projectdaomysql")
public class ProjectDaoImplMySQL implements ProjectDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("employeedaomysql")
	private EmployeeDaoimplMySQL employeedao;
	
	@Autowired
	@Qualifier("projectmemberdaomysql")
	private ProjectMemberDao projectMemberDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addProject(Project project) {
		String sql = "insert into project(projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate) values(?,?,?,?,?,?)";

		return jdbcTemplate.update(sql, project.getProjectId(), project.getProjectName(), project.getProjectContent(),
				project.getProjectOwner().getEmployeeId(), project.getProjectStartDate(), project.getProjectEndDate());
	}

	@Override
	public int removeProjectById(String projectId) {
		String sql = "delete from project where projectId = ? ";
		return jdbcTemplate.update(sql, projectId);
	}
	
	RowMapper<Project> projectMapper = (ResultSet rs, int rowNum) -> {
		Project project = new Project();
		project.setProjectId(rs.getString("projectId"));
		project.setProjectName(rs.getString("projectName"));
		project.setProjectContent(rs.getString("projectContent"));
		
		project.setProjectOwner(employeedao.findEmployeeById(rs.getInt("projectOwner")).get());
		
		List<ProjectMember> projectMembers = projectMemberDao.findProjectMemberById(project.getProjectId());
		List<Employee> employees = new ArrayList<Employee>();
		for(ProjectMember projectMember:projectMembers) {
			Employee employee = employeedao.findEmployeeById(projectMember.getEmployeeId()).get();
			employees.add(employee);
		}
		project.setProjectMembers(employees);
		
		project.setProjectStartDate(rs.getDate("projectStartDate"));
		project.setProjectEndDate(rs.getDate("projectEndDate"));
		
		return project;
	};
	
	@Override
	public List<Project> findAllProjects() {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project";
		return jdbcTemplate.query(sql, projectMapper);
	}

	@Override
	public Optional<Project> findProjectById(String projectId) {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project where projectId";
		try {
			Project project = jdbcTemplate.queryForObject(sql, projectMapper, projectId);
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
