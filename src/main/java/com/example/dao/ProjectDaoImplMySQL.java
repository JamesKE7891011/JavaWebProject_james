package com.example.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	//與JdbcTemplate 相互依賴(不需要手動實例化，降低了耦合性)
	//Spring Framework 提供的 JDBC 操作的模板，被注入到 ProjectDaoImplMySQL 類別中，使得該類別能夠透過它來執行 SQL 操作。
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("employeedaomysql")
	private EmployeeDaoimplMySQL employeedao;
	
	@Autowired
	@Qualifier("projectmemberdaomysql")
	private ProjectMemberDao projectMemberDao;

	
	// 新增專案：
	@Override
	@Transactional(propagation = Propagation.REQUIRED) // 確保這些資料庫操作要麼全部成功執行，要麼全部失敗回滾，以保證資料的一致性。(REQUIRED:若是有現有事務，就加入；如果沒有，就創建一個新事務。)
	public int addProject(Project project) {
		String sql = "insert into project(projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate) values(?,?,?,?,?,?)";
		
		// 返回更新的資料行數，成功返回一般為一個>0的正整數
		return jdbcTemplate.update(sql, project.getProjectId(), project.getProjectName(), project.getProjectContent(),
				project.getProjectOwner().getEmployeeId(), project.getProjectStartDate(), project.getProjectEndDate());
	}

	// 定義了一個RowMapper物件，用於"映射"ResultSet中的資料到Project物件(映射:將從資料庫中擷取的資料轉換為應用程式中的對應的Project物件。)
	RowMapper<Project> projectMapper = (ResultSet rs, int rowNum) -> {
		
		 // 創建一個新的Project物件
		Project project = new Project();
		
		// 設定Project的基本屬性
		project.setProjectId(rs.getString("projectId"));
		project.setProjectName(rs.getString("projectName"));
		project.setProjectContent(rs.getString("projectContent"));
		
		// 將資料庫中的Employee設定為ProjectOwner
		project.setProjectOwner(employeedao.findEmployeeById(rs.getInt("projectOwner")).get());
		
		// 查詢並設定與該Project相關的所有ProjectMember
		List<ProjectMember> projectMembers = projectMemberDao.findProjectMemberById(project.getProjectId());
		List<Employee> employees = new ArrayList<Employee>();
		
		// 迭代ProjectMember的集合，查詢並設定相應的Employee，加入到List中(迭代是指遍歷（或循環）一個集合，逐一處理集合中的每一個元素。)
		for(ProjectMember projectMember:projectMembers) {
			Employee employee = employeedao.findEmployeeById(projectMember.getEmployeeId()).get();
			employees.add(employee);
		}
		
		// 設定Project的成員列表
		project.setProjectMembers(employees);
		
		project.setProjectStartDate(rs.getDate("projectStartDate"));
		project.setProjectEndDate(rs.getDate("projectEndDate"));
		
		// 返回完整設定的Project物件
		return project;
	};
	
	// 根據專案ID刪除指定的專案
	@Override
	public int removeProjectById(String projectId) {
		String sql = "delete from project where projectId = ?";
		
		// 表示刪除操作受影響的資料行數的整數，成功->資料行數應該大於 0
		return jdbcTemplate.update(sql, projectId);
	}
	
	// 查詢專案(多筆)
	@Override
	public List<Project> findAllProjects() {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project";
		
		// 使用 JdbcTemplate 的 query 方法執行 SQL 查詢，並使用 projectMapper 將 ResultSet 中的資料映射到 Project 物件
		return jdbcTemplate.query(sql, projectMapper);
	}
	
	//根據專案ID查找專案(單筆)
	@Override
	public Optional<Project> findProjectById(String projectId) {
		String sql = "select projectId,projectName,projectContent,projectOwner,projectStartDate,projectEndDate from project where projectId =?";
		try {
			
			//  使用 JdbcTemplate 的 queryForObject 方法執行 SQL 查詢，並使用 projectMapper 將 ResultSet 中的資料映射到 Project 物件
			Project project = jdbcTemplate.queryForObject(sql, projectMapper, projectId);
			
			// 使用 Optional 類型來包裝可能為 null 的 project 物件，以避免空指針異常。
			return Optional.ofNullable(project);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	// 更新專案內容
	@Override
	public int updateProject(Project projectUpdate) {
		String sql = "update project set projectName = ?, projectContent = ?, projectOwner = ?, projectStartDate = ?, projectEndDate = ? where projectId = ?";
		return jdbcTemplate.update(sql, 
				projectUpdate.getProjectName(), 
				projectUpdate.getProjectContent(),
				projectUpdate.getProjectOwner().getEmployeeId(), 
				projectUpdate.getProjectStartDate(), 
				projectUpdate.getProjectEndDate(),
				projectUpdate.getProjectId());
	}


}
