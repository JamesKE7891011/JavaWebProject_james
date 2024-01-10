package com.example.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.bean.IssueFile;

public interface IssueFileDao {
	
	//新增檔案
	int[] addIssueFile(Integer issueId,List<MultipartFile> issuePaths);
	
	//移除檔案
	int removeIssueFile(Integer issueId);
	
	//根據Issue Id 查詢所有Issue
	List<IssueFile> findAllIssueFiles();
	
	//根據Issue Id 查詢 Issue
	List<IssueFile> findIssueFilesByIssueId(int issueId);
	
	//更新Issue檔案
	int updateIssueFile(IssueFile issueFileUpdate);
	
	//根據Issuefile Id 查詢 Issuefile
	List<IssueFile> findIssueFilesByIssueFileId(Integer issueFileId);



	

	
}
