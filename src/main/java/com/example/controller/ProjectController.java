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
	
	//查詢專案
	@GetMapping
	public String getProjectPage(Model model) {
		
		// 1. projects
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);

		for(Project project:projects) {
			
			// owner id -> owner employee name
			Employee owner = employeedao.findEmployeeById( Integer.parseInt(project.getProjectOwner())).get();
			project.setProjectOwner(owner.getEmployeeName());
			
			List<ProjectMember> projectMembers = projectMemberDao.findProjectMemberById(project.getProjectId());
			List<Employee> employees = new ArrayList<Employee>();
			for(ProjectMember projectMember:projectMembers) {
				Employee employee = employeedao.findEmployeeById(projectMember.getEmployeeId()).get();
				employees.add(employee);
			}
			project.setProjectMembers(employees);
		}
		
		System.out.println(projects);
		
		// 2. members
		List<Employee> employees = employeedao.findAllEmployees();
		model.addAttribute("employees", employees);
		
		return "/backend/ProjectCreate";
	}
	
	//新增專案
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addproject", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=utf-8")
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
			projectMemberDao.addProjectMember(projectId, members);
			if (rowcount == 0) {
				model.addAttribute("errorMessage", "新增失敗，請通知管理員");
				return "backend/ProjectCreate";
			}else {
				return "redirect:/mvc/project";
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
	@GetMapping("/cancelproject/{projectId}" )
	public String cancelProject(@PathVariable("projectId") String projectId,Model model) {
	    try {
	        int rowcount = projectMemberDao.removeProjectMember(projectId); // 刪除專案成員
	        if (rowcount >= 0) {
	            rowcount = projectDao.removeProjectById(projectId); // 刪除專案
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
	                            @RequestParam(name = "projectOwner") String newprojectOwner,
	                            @RequestParam(name = "projectMember") String newprojectMember,
	                            @RequestParam(name = "projectStartDate") Date newprojectStartDate,
	                            @RequestParam(name = "projectEndDate") Date newprojectEndDate,
	                            HttpSession session, Model model) throws ParseException {
	    try {
	        Project projectUpdate = new Project();
	        projectUpdate.setProjectId(projectId);
	        projectUpdate.setProjectName(newprojectName);
	        projectUpdate.setProjectContent(newprojectContent);
	        projectUpdate.setProjectOwner(newprojectOwner);
	        projectUpdate.setProjectStartDate(newprojectStartDate);
	        projectUpdate.setProjectEndDate(newprojectEndDate);

	        // projectMember: 將 1,2,3 轉成 List<Integer>
	        List<Integer> newMembers = Arrays.asList(newprojectMember.split(","))
	                .stream()
	                .mapToInt(Integer::parseInt)
	                .boxed()
	                .collect(Collectors.toList());

	        // 更新專案訊息
	        int updateResult = projectDao.updateProject(projectUpdate);

	        if (updateResult == 0) {
	            return "專案修改失敗";
	        }

	        // 更新專案成員
	        projectMemberDao.removeProjectMember(projectId);
	        projectMemberDao.addProjectMember(projectId, newMembers); 

	        return "redirect:/mvc/project";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "專案修改失敗：" + e.getMessage();
	    }
	}	
	
}
	
	

	
	
	
	