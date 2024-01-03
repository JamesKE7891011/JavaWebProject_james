package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.IssueClass;

public interface IssueClassDao {
	
	List<IssueClass> findAllIssueClass();
	
	Optional<IssueClass> findIssueClassById(String issueClassId);
	
}
