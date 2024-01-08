package com.example.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Issue;
import com.example.bean.IssueFile;
import com.example.bean.Project;
import com.mysql.cj.protocol.Resultset;

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
		String sql = "insert into issue(projectId,issueName,issueClassId,issueContent,issueStatus) values(?,?,?,?,?)";
		return jdbcTemplate.update(sql, issue.getProjectId(),issue.getIssueName(),issue.getIssueClassId(),issue.getIssueContent(),issue.getIssueStatus());
	}

	@Override
	public int removeIssueById(Integer issueId) {
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
		issue.setProjectId(rs.getString("projectId"));
		issue.setIssueClassId(rs.getString("issueClassId"));
		issue.setIssueContent(rs.getString("issueContent"));
		issue.setIssueStatus(rs.getInt("issueStatus"));
		issue.setIssueDateTime(rs.getDate("issueDateTime"));
		
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
	public Optional<Issue> findIssuesByProjectId(String projectId) {
		String sql = "select issueId,projectId,issueName,issueClassId,issueContent,issueStatus,issueDateTime from issue where projectId = ?";
		try {
			Issue issue = jdbcTemplate.queryForObject(sql,issueMapper,projectId);
			return Optional.ofNullable(issue);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public int updateIssue(Issue issueUpdate) {
		String sql = "update issue set projectId = ?,issueName = ?,issueClassId = ?,issueContent = ?,issueStatus = ?,issueDateTime = ? where issueId = ?";
		return jdbcTemplate.update(sql, 
				issueUpdate.getProjectId(),
				issueUpdate.getIssueName(),
				issueUpdate.getIssueClassId(),
				issueUpdate.getIssueContent(),
				issueUpdate.getIssueStatus());
	}	
}
