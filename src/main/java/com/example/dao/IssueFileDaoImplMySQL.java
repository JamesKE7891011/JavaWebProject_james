package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.IssueFile;


@Repository("issuefiledaomysql")
public class IssueFileDaoImplMySQL implements IssueFileDao{
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int[] addIssueFile(Integer issueId, List<MultipartFile> issuePaths) {

	    String sql = "insert into issuefile(issueId, issueFilePath) values(?, ?)";

	    BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {

	        @Override
	        public void setValues(PreparedStatement ps, int i) throws SQLException {
	            ps.setInt(1, issueId);

	            // 從 MultipartFile 中提取檔案路徑
	            String filePath = extractFilePath(issuePaths.get(i));

	            ps.setString(2, filePath);
	        }

	        @Override
	        public int getBatchSize() {
	            return issuePaths.size();
	        }
	    };

	    return jdbcTemplate.batchUpdate(sql, bps);
	}
	
	private String extractFilePath(MultipartFile multipartFile) {
	    // 從 MultipartFile 中提取檔案路徑的邏輯
	    // 例如：
	    // return "C:/uploads/" + multipartFile.getOriginalFilename();
	    return "C:/uploads/" + multipartFile.getOriginalFilename();
	}

	@Override
	public int removeIssueFile(Integer issueId) {
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
		String sql = "update issuefile set issueId = ?,issueFilePath = ?";
		return jdbcTemplate.update(sql,
		issueFileUpdate.getIssueId(),
		issueFileUpdate.getIssueFilePath());	
	}

	@Override
	public Optional<IssueFile> findIssueFileByIssueFileId(Integer issueFileId) {
		String sql = "select issueFileId,issueID,issueFilePath from issuefile where issueFileId = ?";
		try {
			IssueFile issueFile = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(IssueFile.class), issueFileId);
			return Optional.ofNullable(issueFile);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	
	
	
}
