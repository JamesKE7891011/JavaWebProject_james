package com.example.dao;

import java.util.List;

import com.example.bean.IssueFile;

public interface IssueFileDao {
	
	//新增檔案
	int[] addIssueFile(Integer issueId,List<Integer> issueFiles);
	
	//移除檔案
	int removeIssueFile(String issueId);
	
	//根據Issue Id 查詢所有Issue
	List<IssueFile> findAllIssueFiles();
	
	//根據Issue Id 查詢 Issue
	List<IssueFile> findIssueFilesByIssueId(int issueId);
	
	//更新Issue檔案
	int updateIssueFile(IssueFile issueFileUpdate);

	

	
}
