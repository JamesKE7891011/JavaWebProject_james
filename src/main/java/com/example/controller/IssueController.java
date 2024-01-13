package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.ContentDisposition;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@RequestMapping(value = "/download/{issueId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("issueId") Integer issueId) throws IOException {
	    List<IssueFile> issueFiles = issueFileDao.findIssueFilesByIssueId(issueId);

	    if (issueFiles.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    IssueFile issueFile = issueFiles.get(0);
	    String filePath = issueFile.getIssueFilePath();
	    File file = new File(filePath);

	    if (!file.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    byte[] fileContent = FileUtils.readFileToByteArray(file);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", issueFile.getIssueFilePath());

	    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
	
	//取消專案議題
	@GetMapping("/cancelissue/{issueId}")
	@ResponseBody
	public String cancelIssue(@PathVariable("issueId") Integer issueId, Model model) {
	    try {
	        int rowcount = issueDao.removeIssueById(issueId);
	        if (rowcount > 0) {
	            // 删除成功，直接重定向到 issue 页面
	            return "redirect:/mvc/issue";
	        } else {
	            // 删除失败，显示错误消息
	            model.addAttribute("errorMessage", "刪除失敗，請通知管理員");
	            return "frontend/Issue";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 删除过程中出现异常，显示异常消息
	        model.addAttribute("errorMessage", "刪除時發生異常，請通知管理員");
	        return "frontend/Issue";
	    }
	}
	
	@PutMapping(value = "/{issueId}/updateissue")
	public String updateIssueStatus(@PathVariable("issueId") Integer issueId,
	                                @RequestParam("issueStatus") Integer newIssueStatus,
	                                HttpSession session, Model model) {
	    try {
	        // 創建一個新的 Issue 對象並設置新的狀態
	        Issue issueUpdate = new Issue();
	        issueUpdate.setIssueId(issueId);  // 設置問題的 ID
	        issueUpdate.setIssueStatus(newIssueStatus);

	        // 調用 DAO 層的 updateIssue 方法進行更新
	        int updateResult = issueDao.updateIssue(issueUpdate);

	        // 檢查更新結果
	        if (updateResult == 0) {
	            return "專案修改失敗";
	        }

	        // 更新成功時，可以進行一些額外的操作，例如更新 Model 或記錄日誌等

	        // 返回成功消息或重定向到適當的頁面
	        return "專案修改成功";
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 在發生異常時返回錯誤消息
	        return "專案修改失敗: " + e.getMessage();
	    }
	}

									
	


}

