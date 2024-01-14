package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Issue;

public interface IssueDao {
	
	int addIssue(Issue issue);
	
	int removeIssueById(Integer issueId);
	
	List<Issue> findAllIssues();
	
	Optional<Issue> findIssuesByIssueId(Integer issueId);
	
	List<Issue> findIssuesByProjectId(String projectId);
	
	Boolean closeIssueStatusByIssueId(Integer issueId);
	
	Boolean openIssueStatusByIssueId(Integer issueId);

	
}
