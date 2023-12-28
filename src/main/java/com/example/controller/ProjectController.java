package com.example.controller;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.el.parser.ParseException;
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
import com.example.dao.EmployeeDaoimplMySQL;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectMemberDao;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	@Qualifier("projectdaomysql")
	private ProjectDao projectDao;
	
	@Autowired
	@Qualifier("projectmemberdaomysql")
	private ProjectMemberDao projectMemberDao;
	
	@Autowired
	@Qualifier("employeedaomysql")
	private EmployeeDaoimplMySQL employeedao;
	

	@GetMapping
	public String getProjectPage(Model model) {
		
		// 1. projects
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);
		
		// 2. members
		List<Employee> employees = employeedao.findAllEmployees();
		model.addAttribute("employees", employees);
		
		return "/backend/ProjectCreate";
	}
	
	
	//新增專案
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addproject", method = {RequestMethod.GET,RequestMethod.POST},produces = "text/plain;charset=utf-8")
	public String addProject(@RequestParam(name = "projectId")String projectId,
							 @RequestParam(name = "projectName")String projectName,
							 @RequestParam(name = "projectContent")String projectContent,
							 @RequestParam(name = "projectOwner")String projectOwner,
							 @RequestParam(name = "projectMember") String projectMember,
							 @RequestParam(name = "projectStartDate")Date projectStartDate,
							 @RequestParam(name = "projectEndDate")Date projectEndDate,HttpSession session,Model model) throws ParseException{
		try {
			
			Project project = new Project();
			project.setProjectId(projectId);
			project.setProjectName(projectName);
			project.setProjectContent(projectContent);
			project.setProjectOwner(projectOwner);
			project.setProjectStartDate(projectStartDate);
			project.setProjectEndDate(projectEndDate);
			
			// projectMember: 將 1,2,3 轉成 List<Integer>
			List<Integer> members = Arrays.asList(projectMember.split(","))
					.stream()
					.mapToInt(Integer::parseInt)
					.boxed()
					.collect(Collectors.toList());

			int rowcount = projectDao.addProject(project);
			projectDao.addProjectMember(projectId, members);
			if (rowcount == 0) {
				model.addAttribute("errorMessage", "新增失敗，請通知管理員");
				return "backend/ProjectCreate";
			}else {
				return "redirect:/mvc/project/viewprojects";
			}
		}catch (SQLIntegrityConstraintViolationException e) {
			model.addAttribute("errorMessage", "Project 已經建立");
			return "backend/ProjectCreate";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "新增失敗，請通知管理員" + e.getMessage());
			return "backend/ProjectCreate";
		}		
	}
	
	// 取消專案
	@GetMapping("/cancelproject/{projectId}")
	@ResponseBody
	public String cancelProject(@PathVariable("projectId") String projectId) {
	    int rowcount = projectDao.removeprojectById(projectId);
	    return rowcount == 1 ? "專案取消失敗" : "專案取消成功";
	}
	
	//查看所有專案內容
	@GetMapping(value = "/viewprojects", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String findAllProjects(Model model) {
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);
		return "backend/ProjectCreate";
	}
	
	
	// 修改專案內容
	@RequestMapping(value = "/{projectId}/updateproject", method = {RequestMethod.PUT, RequestMethod.POST}, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String updateProject(@PathVariable("projectId") String projectId,
	                            @RequestParam(name = "projectName") String newprojectName,
	                            @RequestParam(name = "content") String newcontent,
	                            @RequestParam(name = "owner") String newowner,
	                            @RequestParam(name = "members") List<Employee> newmembers,
	                            @RequestParam(name = "startDate") Date newstartDate,
	                            @RequestParam(name = "endDate") Date newendDate) {
		
		Project projectUpdate = new Project();
		projectUpdate.setProjectId(projectId);
		projectUpdate.setProjectName(newprojectName);
		projectUpdate.setProjectContent(newcontent);
		projectUpdate.setProjectMembers(newmembers);
		projectUpdate.setProjectStartDate(newstartDate);
		projectUpdate.setProjectEndDate(newendDate);
		
	    return projectDao.updateProject(projectUpdate) == 0 ? "專案修改失敗" : "專案修改成功";
	}
	
	
	
}
	
	

	
	
	
	