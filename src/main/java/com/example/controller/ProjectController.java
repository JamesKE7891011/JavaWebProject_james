package com.example.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bean.Employee;
import com.example.bean.Project;
import com.example.bean.ProjectMember;
import com.example.dao.EmployeeDaoimplMySQL;
import com.example.dao.IssueDao;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectMemberDao;

@Controller      // 標記這個類別為控制器。控制器負責處理HTTP請求，並返回相應的視圖或數據。
@RequestMapping("/project")  
public class ProjectController {
	
	@Autowired  //是Spring框架的注解，用於自動注入相依的Bean。
	@Qualifier("projectdaomysql")
	private ProjectDao projectDao;
	
	@Autowired
	@Qualifier("projectmemberdaomysql")
	private ProjectMemberDao projectMemberDao;
	
	@Autowired
	@Qualifier("employeedaomysql")
	private EmployeeDaoimplMySQL employeedao;
	
	@Autowired
	@Qualifier("issuedaomysql")
	private IssueDao issueDao;
	
	private void addBasicModel(Model model) {
		// 查詢所有專案
		List<Project> projects = projectDao.findAllProjects();
				
		// 將查詢到的專案列表添加到Model，以便在視圖中使用。
		model.addAttribute("projects", projects);
				
		// 查詢所有員工
		List<Employee> employees = employeedao.findAllEmployees();
		model.addAttribute("employees", employees);
	}
	
	//查詢專案
	@GetMapping
	public String getProjectPage(Model model) {
		
		// 查詢所有專案
		List<Project> projects = projectDao.findAllProjects();
		
		// 將查詢到的專案列表添加到Model，以便在視圖中使用。
		model.addAttribute("projects", projects);
		
		// 查詢所有員工
		List<Employee> employees = employeedao.findAllEmployees();
		model.addAttribute("employees", employees);
			
		return "/backend/ProjectCreate";
	}
	
	@GetMapping("/backendfilter")
	public String backendFilter(HttpSession session,Model model){
		
		String role = (String)session.getAttribute("role");
		
		if(role != null && "admin".equalsIgnoreCase(role)) {
				addBasicModel(model);
			return "/backend/ProjectCreate";
		}
		
		return "redirect:/mvc/fwbackendpage.jsp";
	}
	
	//新增專案
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addproject", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=utf-8")
	public String addProject(@RequestParam(name = "projectId")String projectId,
							 @RequestParam(name = "projectName")String projectName,
							 @RequestParam(name = "projectContent")String projectContent,
							 @RequestParam(name = "projectOwner") Integer projectOwner,
							 @RequestParam(name = "projectMember") String projectMember,
							 @RequestParam(name = "projectStartDate")Date projectStartDate,
							 @RequestParam(name = "projectEndDate")Date projectEndDate,HttpSession session,Model model) throws ParseException{  // ParseException 用於處理與日期和時間解析相關的錯誤
		try {
			
			 // 建立 Project 物件，設定相關屬性
			Project project = new Project();
			project.setProjectId(projectId);
			project.setProjectName(projectName);
			project.setProjectContent(projectContent);
			
			// 取得的員工物件設定為專案物件的擁有者
			project.setProjectOwner(employeedao.findEmployeeById(projectOwner).get());
			
			project.setProjectStartDate(projectStartDate);
			project.setProjectEndDate(projectEndDate);
			
			// 將 projectMember 轉換成 List<Integer>，才能塞進 addProjectMember()
			List<Integer> members = Arrays.asList(projectMember.split(","))
					.stream()
					.mapToInt(Integer::parseInt)
					.boxed()
					.collect(Collectors.toList());

			// 呼叫 ProjectDao 的 addProject 方法，儲存專案資訊
			int rowcount = projectDao.addProject(project);
			
			// 呼叫 ProjectMemberDao 的 addProjectMember 方法，儲存專案成員資訊
			projectMemberDao.addProjectMember(projectId, members);
			
			// 根據執行結果進行導向
			if (rowcount == 0) {
				model.addAttribute("errorMessage", "新增失敗，請通知管理員");
				return "backend/ProjectCreate";
			}else {
				return "redirect:/mvc/project";
			}
		}catch (SQLIntegrityConstraintViolationException e) {  // 發生資料庫完整性約束違反時，提供一個友好的錯誤訊息給使用者
			model.addAttribute("errorMessage", "Project 已經建立");
			return "backend/ProjectCreate";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "新增失敗，請通知管理員" + e.getMessage());
			return "backend/ProjectCreate";
		}		
	}
	
	// 取消專案
	@GetMapping("/cancelproject/{projectId}" )
	public String cancelProject(@PathVariable("projectId") String projectId,Model model) {
	    try {
	        int rowcount1 = projectMemberDao.removeProjectMember(projectId); // 刪除專案成員
	        int rowcount2 = issueDao.removeIssueByProjectId(projectId);		 // 再刪除專案內議題
	        if (rowcount1 > 0 && rowcount2 > 0) {
	            projectDao.removeProjectById(projectId); // 才可以刪除專案
	            return "redirect:/mvc/project";
	        } else {
	        	model.addAttribute("errorMessage","刪除失敗，請通知管理員");
	            return "backend/ProjectCreate";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "backend/ProjectCreate" + e.getMessage();
	    }
	}

	// 更新專案
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/{projectId}/updateproject", method = {RequestMethod.PUT, RequestMethod.POST}, produces = "text/plain;charset=utf-8")
	public String updateProject(@PathVariable("projectId") String projectId,
	                            @RequestParam(name = "projectName") String newprojectName,
	                            @RequestParam(name = "projectContent") String newprojectContent,
	                            @RequestParam(name = "projectOwner") Integer newprojectOwner,
	                            @RequestParam(name = "projectMember") String newprojectMember,
	                            @RequestParam(name = "projectStartDate") Date newprojectStartDate,
	                            @RequestParam(name = "projectEndDate") Date newprojectEndDate,
	                            HttpSession session, Model model) throws ParseException {
	    
		// 創建一個 Project 對象用於更新專案訊息
		try {
	        Project projectUpdate = new Project();
	        projectUpdate.setProjectId(projectId);
	        projectUpdate.setProjectName(newprojectName);
	        projectUpdate.setProjectContent(newprojectContent);
	        projectUpdate.setProjectOwner(employeedao.findEmployeeById(newprojectOwner).get());
	        projectUpdate.setProjectStartDate(newprojectStartDate);
	        projectUpdate.setProjectEndDate(newprojectEndDate);

	        // 將 newprojectMember 字串轉換成 List<Integer>
	        List<Integer> newMembers = Arrays.asList(newprojectMember.split(","))
	                .stream()
	                .mapToInt(Integer::parseInt)
	                .boxed()
	                .collect(Collectors.toList());
	        
	        // 刪除原有專案成員，並新增新的專案成員
	        projectMemberDao.removeProjectMember(projectId);
	        projectMemberDao.addProjectMember(projectId, newMembers); 

	        // 更新專案訊息，並獲取更新的行數
	        int updateResult = projectDao.updateProject(projectUpdate);
	        
	        // 檢查更新結果
	        if (updateResult == 0) {
	            return "專案修改失敗";
	        }
	        
	        // 更新成功，重定向到專案列表頁面
	        return "redirect:/mvc/project";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "專案修改失敗：" + e.getMessage();
	    }
	}	
	
}
	
	

	
	
	
	