package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.Issue;
import com.example.bean.IssueClass;
import com.example.bean.IssueFile;
import com.example.bean.Project;
import com.example.dao.IssueClassDao;
import com.example.dao.IssueDao;
import com.example.dao.IssueFileDao;
import com.example.dao.ProjectDao;

@Controller
@RequestMapping("/issue")
public class IssueController {

	private static final String uploadDir = "C:/uploads/";

	@Autowired
	@Qualifier("issuedaomysql")
	private IssueDao issueDao;

	@Autowired
	@Qualifier("issueclassdaomysql")
	private IssueClassDao issueClassDao;

	@Autowired
	@Qualifier("issuefiledaomysql")
	private IssueFileDao issueFileDao;

	@Autowired
	@Qualifier("projectdaomysql")
	private ProjectDao projectDao;

	// 查詢Issue
	@GetMapping()
	public String getIssuePage(Model model) {

		// 1. issues
		List<Issue> issues = issueDao.findAllIssues();
		model.addAttribute("issues", issues);

		// 2.issuefiles
		List<IssueFile> issuefiles = issueFileDao.findAllIssueFiles();
		model.addAttribute("issuefiles", issuefiles);

		// 3.issueclass
		List<IssueClass> issueClasses = issueClassDao.findAllIssueClass();
		model.addAttribute("issueClasses", issueClasses);

		// 4.project
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);

		return "/frontend/Issue";
	}
	
	@GetMapping(value = "/{projectId}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<Issue> findIssueById(@PathVariable("projectId") String projectId) {
		return issueDao.findIssuesByProjectId(projectId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addissue", method = {RequestMethod.POST }, produces = "text/plain;charset=utf-8")
	public String addIssue(@RequestParam(name = "projectId") String projectId,
			@RequestParam(name = "issueName") String issueName,
			@RequestParam(name = "issueClassId") String issueClassId,
			@RequestParam(name = "issueContent") String issueContent,
			@RequestParam(name = "issueFilePath") List<MultipartFile> issueFilePaths, HttpSession session, Model model)
			throws Exception{
		
		Issue issue = new Issue();
		issue.setProjectId(projectId);
		issue.setIssueName(issueName);
		issue.setIssueClassId(issueClassId);
		issue.setIssueContent(issueContent);

		int issueId = issueDao.addIssue(issue);
		if (issueId == -1) {
			model.addAttribute("errorMessage", "新增失敗，請通知管理員");
			return "/frontend/Issue"; // 返回前端頁面，提供錯誤信息
		} 
		
		if (issueFilePaths != null) {
			for (MultipartFile issueFilePath : issueFilePaths) {
				String fileName = issueFilePath.getOriginalFilename();
				// 將檔案寫入指定目錄
				File uploadFile = new File(uploadDir + fileName);
				issueFilePath.transferTo(uploadFile);
			}
			issueFileDao.addIssueFile(issueId, issueFilePaths);
		}
		return "redirect:/mvc/issue";
	}
}
