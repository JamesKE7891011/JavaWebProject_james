package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Issue;
import com.example.bean.IssueFile;

@Repository("issuedaomysql")
public class IssueDaoImplMySQL implements IssueDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("issuefiledaomysql")
	private IssueFileDao issueFileDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addIssue(Issue issue) {
		int issueId = -1;
	    String sql = "insert into issue(projectId, issueName, issueClassId, issueContent) values(?,?,?,?)";

	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    int affectedRows = jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, issue.getProjectId());
	        ps.setString(2, issue.getIssueName());
	        ps.setString(3, issue.getIssueClassId());
	        ps.setString(4, issue.getIssueContent());
	        return ps;
	    }, keyHolder);

	    // Now you can retrieve the generated key
	    if (keyHolder.getKey() != null) {
	        issueId = keyHolder.getKey().intValue();
	    }
	    return issueId;
	}
		
	@Override
	public int removeIssueByProjectId(String projectId) {
		String sql = "delete from issue where projectId = ?";
		return jdbcTemplate.update(sql,projectId);
	}

	@Override
	public int removeIssueById(Integer issueId ) {
	    
		// 刪除相應的子表記錄
	    String deleteIssueFileSql = "delete from issuefile where issueId = ?";
	    jdbcTemplate.update(deleteIssueFileSql, issueId);

	    // 現在可以刪除父表記錄
	    String deleteIssueSql = "delete from issue where issueId = ?";
	    return jdbcTemplate.update(deleteIssueSql, issueId);
	}
	
	RowMapper<Issue> issueMapper = (ResultSet rs, int rowNum) -> {
		Issue issue = new Issue();
		issue.setIssueId(rs.getInt("issueId"));
		issue.setIssueName(rs.getString("issueName"));
		issue.setProjectId(rs.getString("projectId"));
		issue.setIssueClassId(rs.getString("issueClassId"));
		issue.setIssueContent(rs.getString("issueContent"));
		issue.setIssueStatus(rs.getInt("issueStatus"));
		issue.setIssueDateTime(rs.getTimestamp("issueDateTime"));
	    List<IssueFile> issueFiles = issueFileDao.findIssueFilesByIssueId(rs.getInt("issueId"));
	    issue.setIssueFiles(issueFiles);
		return issue;
	};

	@Override
	public List<Issue> findAllIssues() {
		String sql = "select issueId,projectId,issueName,issueClassId,issueContent,issueStatus,issueDateTime from issue";
		return jdbcTemplate.query(sql, issueMapper);
	}

	@Override
	public Optional<Issue> findIssuesByIssueId(Integer issueId) {
		String sql = "select issueId,projectId,issueName,issueClassId,issueContent,issueStatus,issueDateTime from issue where issueId = ?";
		try {
			Issue issue = jdbcTemplate.queryForObject(sql,issueMapper,issueId);
			return Optional.ofNullable(issue);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Issue> findIssuesByProjectId(String projectId) {
		String sql = "select issueId,projectId,issueName,issueClassId,issueContent,issueStatus,issueDateTime from issue where projectId = ?";
		return jdbcTemplate.query(sql, issueMapper,projectId);
	}

	@Override
	public Boolean closeIssueStatusByIssueId(Integer issueId) {
		String sql ="update issue set issueStatus = 0  where issueId = ?";
		return jdbcTemplate.update(sql, issueId) == 0;
	}

	@Override
	public Boolean openIssueStatusByIssueId(Integer issueId) {
		String sql ="update issue set issueStatus = 1 where issueId = ?";
		return jdbcTemplate.update(sql, issueId)== 1;
	}
		
}
