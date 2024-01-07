package com.example.controller;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bean.Issue;
import com.example.bean.IssueFile;
import com.example.dao.IssueClassDao;
import com.example.dao.IssueDao;
import com.example.dao.IssueFileDao;

@Controller
@RequestMapping("/issue")
public class IssueController {
	
//	@GetMapping
//	public String getIssuePage() {
//		return "/frontend/Issue";
//	}
	
	@Autowired
	@Qualifier("issuedaomysql")
	private IssueDao issueDao;
	
	@Autowired
	@Qualifier("issueclassdaomysql")
	private IssueClassDao issueClassDao;
	
	@Autowired
	@Qualifier("issuefiledaomysql")
	private IssueFileDao issueFileDao;
	
	//查詢Issue
	@GetMapping
	public String getIssuePage(Model model) {
		
		// 1. issues
		List<Issue> issues = issueDao.findAllIssues();
		model.addAttribute("issues", issues);
		
		//2.issuefiles
		List<IssueFile> issuefiles = issueFileDao.findAllIssueFiles();
		model.addAttribute("issuefiles", issuefiles);
		
		return "/frontend/Issue";
	}
	
	//新增Issue
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addissue", method = {RequestMethod.GET,RequestMethod.POST},produces = "text/plain;charset=utf-8")
	public String addIssue(@RequestParam(name = "projectId")String projectId,
						   @RequestParam(name = "issueName")String issueName,
						   @RequestParam(name = "issueClass")String issueClass,
						   @RequestParam(name = "issueContent")String issueContent,
						   @RequestParam(name = "issueFile") String issueFile,
						   HttpSession session,Model model) throws ParseException {
		try {
				Issue issue = new Issue();
				issue.setProjectId(projectId);
				issue.setIssueName(issueName);
				issue.setIssueClass(issueClass);
				issue.setIssueContent(issueContent);
			
				//將issueFile轉成 List<Integer>
				List<Integer> issueFiles = Arrays.asList(issueFile.split(","))
                        .stream()
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());
			
				int rowcount = issueDao.addIssue(issue);
				issueFileDao.addIssueFile(issue.getIssueId(), issueFiles);
			
				if (rowcount == 0) {
				model.addAttribute("errorMessage", "新增失敗，請通知管理員");			
				return "/frontend/Issue";			
				}else {
				return "redirect:/mvc/issue";
				}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "新增失敗，請通知管理員" + e.getMessage());
			return "/frontend/Issue";
		}
	}
	
}
		
	
	
	




