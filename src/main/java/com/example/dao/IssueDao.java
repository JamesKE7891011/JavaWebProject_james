package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Issue;

public interface IssueDao {
	
	int addIssue(Issue issue);
	
	int removeIssueById(Integer issueId);
	
	List<Issue> findAllIssues();
	
	Optional<Issue> findIssuesByProjectId(String projectId);
	
	int updateIssue(Issue issueUpdate);
	
}
