package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.IssueFile;

@Repository("issuefiledaomysql")
public class IssueFileDaoImplMySQL implements IssueFileDao{
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int[] addIssueFile(Integer issueId, List<Integer> issueFiles) {
		
		String sql = "insert into issuefile(issueId,issueFilePath) values(?,?)";
		
		BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, issueId);
				ps.setInt(2,issueFiles.size());
				
			}
			
			@Override
			public int getBatchSize() {
				return issueFiles.size();
			}
		};
		
		return jdbcTemplate.batchUpdate(sql,bps);
	}

	@Override
	public int removeIssueFile(String issueId) {
		String sql = "delete from issueFile where issueId = ?";
		return jdbcTemplate.update(sql,issueId);
	}
	
	@Override
	public List<IssueFile> findIssueFilesByIssueId(int issueId) {
	    String sql = "select issueFileId,issueID,issueFilePath from issuefile where issueId = ?";
	    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IssueFile.class), issueId);
	}
	
	@Override
	public List<IssueFile> findAllIssueFiles() {
		String sql ="select issueFileId,issueID,issueFilePath from issuefile";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IssueFile.class));
	}

	@Override
	public int updateIssueFile(IssueFile issueFileUpdate) {
		String sql = "update issuefile set issueId = ?,issuePath = ?";
		return jdbcTemplate.update(sql,
		issueFileUpdate.getIssueId(),
		issueFileUpdate.getIssueFilePath());	
	}
	

}
