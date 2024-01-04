package com.example.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.bean.Issue;

public class IssueDaoImplMySQL implements IssueDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addIssue(Issue issue) {
		String sql = "insert into issue(projectId,issueName,issueClass,issueContent) values(?,?,?,?)";
		return jdbcTemplate.update(sql, issue.getProjectId(),issue.getIssueName(),issue.getIssueClass(),issue.getIssueContent());
	}

	@Override
	public int removeIssueById(Integer issueId) {
		String sql = "delete from issue where issueId = ?";
		return jdbcTemplate.update(sql,issueId);
	}

	@Override
	public List<Issue> findAllIssues() {
		String sql = "select issueId,projectId,issueName,issueClass,issueContent,issueStatus,issueDateTime from issue";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Issue.class));
	}

	@Override
	public Optional<Issue> findIssuesByProjectId(String projectId) {
		String sql = "select issueId,projectId,issueName,issueClass,issueContent,issueStatus,issueDateTime from issue where projectId";
		try {
			Issue issue = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Issue.class),projectId);
			return Optional.ofNullable(issue);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public int updateIssue(Issue issueUpdate) {
		String sql = "update issue set projectId = ?,issueName = ?,issueClass = ?,issueContent = ?,issueStatus = ?,issueDateTime = ? where issueId = ?";
		return jdbcTemplate.update(sql, issueUpdate.getProjectId(),issueUpdate.getIssueName(),issueUpdate.getIssueClass(),
										issueUpdate.getIssueContent(),issueUpdate.getIssueDateTime());
	}	
}
