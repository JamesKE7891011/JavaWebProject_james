package com.example.controller;

import java.io.File;
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
	
	//查詢Issue
	@GetMapping
	public String getIssuePage(Model model) {
		
		// 1. issues
		List<Issue> issues = issueDao.findAllIssues();
		model.addAttribute("issues", issues);
		
		//2.issuefiles
		List<IssueFile> issuefiles = issueFileDao.findAllIssueFiles();
		model.addAttribute("issuefiles", issuefiles);
		
		//3.issueclass
		List<IssueClass> issueClasses = issueClassDao.findAllIssueClass();
		model.addAttribute("issueClasses",issueClasses);
		
		//4.project
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);
		
		
		return "/frontend/Issue";
	}
	
	// 新增Issue
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/addissue", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=utf-8")
    public String addIssue(@RequestParam(name = "projectId") String projectId,
            			   @RequestParam(name = "issueName") String issueName, 
            			   @RequestParam(name = "issueClassId") String issueClassId,
            			   @RequestParam(name = "issueContent") String issueContent, 
            			   @RequestParam(name = "issueFile") MultipartFile issueFile,
            			   HttpSession session, Model model) throws ParseException {
        try {
            Issue issue = new Issue();
            issue.setProjectId(projectId);
            issue.setIssueName(issueName);
            issue.setIssueClassId(issueClassId);
            issue.setIssueContent(issueContent);

            // 檢查是否有上傳檔案
            if (issueFile != null && !issueFile.isEmpty()) {
                String fileName = issueFile.getOriginalFilename();

                // 將檔案寫入指定目錄
                File uploadFile = new File(uploadDir + fileName);
                issueFile.transferTo(uploadFile);
            }

            int rowcount = issueDao.addIssue(issue);

            if (rowcount == 0) {
                model.addAttribute("errorMessage", "新增失敗，請通知管理員");
                return "/frontend/Issue"; // 返回前端頁面，提供錯誤信息
            } else {
                return "redirect:/mvc/issue";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "新增失敗，請通知管理員" + e.getMessage());
            return "/frontend/Issue"; // 返回前端頁面，提供錯誤信息
        }
    }

	
}
		
	
	
	




