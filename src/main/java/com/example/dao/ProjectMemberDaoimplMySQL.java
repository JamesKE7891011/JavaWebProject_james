package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.ProjectMember;

@Repository("projectmemberdaomysql")
public class ProjectMemberDaoimplMySQL implements ProjectMemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 新增專案成員
	@Override
	@Transactional(propagation = Propagation.REQUIRED) // 確保這些資料庫操作要麼全部成功執行，要麼全部失敗回滾，以保證資料的一致性。(REQUIRED:若是有現有事務，就加入；如果沒有，就創建一個新事務。)
	public int[] addProjectMember(String projectId, List<Integer> projectMembers) {

		// SQL 插入語句，向 projectMember 表格插入專案成員的資訊
		String sql = "insert into projectmember(projectId, employeeId) values(?,?)";

		// 創建 BatchPreparedStatementSetter，用設定批量更新的每一條記錄的參數
		BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
			
			//設定了每一條 SQL 記錄的參數值(projectId、employeeId)
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, projectId);
				ps.setInt(2, projectMembers.get(i));
			}

			@Override
			public int getBatchSize() {
				
				// 獲取批量更新的記錄數，即 projectMembers 列表的大小
				return projectMembers.size();
			}
		};
		
		// 使用 JdbcTemplate 的 batchUpdate 方法執行批量更新
		return jdbcTemplate.batchUpdate(sql, bps);
	}

	// 根據專案ID刪除該專案成員
	@Override
    public int removeProjectMember(String projectId) {
        
		// SQL 刪除語句，根據 projectId 刪除 projectMember 表格中的相應記錄
		String sql = "delete from projectMember where projectId = ?";
        return jdbcTemplate.update(sql,projectId);
	}

	// 查詢專案ID查詢多名專案成員(多筆)
	@Override
	public List<ProjectMember> findProjectMemberById(String projectId) {
		
		// SQL 查詢語句，根據 projectId 查詢 projectMember 表格中的相應記錄
		String sql = "select projectId,employeeId from projectmember where projectId = ?";
		
		 // 使用 BeanPropertyRowMapper 將查詢結果映射為 ProjectMember 物件的列表
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectMember.class), projectId);
	}
}
