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
