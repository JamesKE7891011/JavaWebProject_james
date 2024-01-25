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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//下載issue內檔案
	@RequestMapping(value = "/download/{issueFileId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("issueFileId") Integer issueFileId) throws IOException {
	    Optional<IssueFile> issueFiles = issueFileDao.findIssueFilesByIssueFileId(issueFileId);

	    if (issueFiles.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    IssueFile issueFile = issueFiles.get();
	    String filePath = issueFile.getIssueFilePath();
	    File file = new File(filePath);

	    if (!file.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    byte[] fileContent = FileUtils.readFileToByteArray(file);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    String filaname = issueFile.getIssueFilePath().substring(issueFile.getIssueFilePath().lastIndexOf("\\")+1);
	    headers.setContentDispositionFormData("attachment", filenameEncode(filaname));

	    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
	
	public static String filenameEncode(String name) {
	    try {
	        return java.net.URLEncoder.encode(name, "UTF-8").replace("+", "%20");
	    } catch (java.io.UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return name;
	    }
	}
	
	//為專題新增issue
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
		
		if (issueFilePaths != null && !issueFilePaths.isEmpty()) {
			boolean hadFiles = false;
			for (MultipartFile issueFilePath : issueFilePaths) {
				String fileName = issueFilePath.getOriginalFilename();
				if(!StringUtils.isEmpty(fileName)) {
					// 將檔案寫入指定目錄
					File uploadFile = new File(uploadDir + fileName);
					issueFilePath.transferTo(uploadFile);
					hadFiles = true;
				}
			}
			if(hadFiles) {
				issueFileDao.addIssueFile(issueId, issueFilePaths);
			}
		}
		return "redirect:/mvc/issue";
	}
	
	
	//取消專案議題
	@GetMapping("/cancelissue/{issueId}")
	@ResponseBody
	public String cancelIssue(@PathVariable("issueId") Integer issueId, Model model) {
	    try {
	        int rowcount = issueDao.removeIssueById(issueId);
	        if (rowcount > 0) {
	            // 删除成功，重導致issue介面
	            return "redirect:/mvc/issue";
	        } else {
	            // 删除失敗，顯示錯誤訊息
	            model.addAttribute("errorMessage", "刪除失敗，請通知管理員");
	            return "frontend/Issue";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 刪除出現異常
	        model.addAttribute("errorMessage", "刪除時發生異常，請通知管理員");
	        return "frontend/Issue";
	    }
	}
	
	@PutMapping(value = "/{issueId}/updateissuestatus")
	@ResponseBody
	public Map<String, Object> updateIssueStatus(@PathVariable(name = "issueId") Integer issueId,
	                                             @RequestBody Map body,
	                                             HttpSession session, Model model) {
		
		Integer newIssueStatus = (Integer) body.get("newIssueStatus");
		
	    Map<String, Object> result = new HashMap<>();
	    
	    try {
	        // 使用 issueDao 中的方法更新 Issue 狀態
	        boolean success;
	        if (newIssueStatus == 1) {
	            // 如果新的狀態是 1，表示要開啟 Issue
	        	success = issueDao.openIssueStatusByIssueId(issueId);
	        } else if (newIssueStatus == 0) {
	            // 如果新的狀態是 0，表示要關閉 Issue
	            success = issueDao.closeIssueStatusByIssueId(issueId);
	        } else {
	            // 其他狀態值可能需要根據具體情況進行處理
	            success = false;
	        }

	        // 檢查更新結果
	        if (success) {
	            result.put("status", "success");
	            result.put("message", "狀態更新成功");
	        } else {
	            result.put("status", "failure");
	            result.put("message", "狀態更新失敗");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        result.put("status", "error");
	        result.put("message", "狀態更新失敗: " + e.getMessage());
	    }
	    return result;
	}

}

