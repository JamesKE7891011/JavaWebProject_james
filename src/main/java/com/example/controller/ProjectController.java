package com.example.controller;

import java.sql.Date;

import java.util.List;

import javax.ws.rs.DELETE;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bean.Employee;
import com.example.bean.Project;
import com.example.bean.ProjectMember;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectMemberDao;


@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@GetMapping
	public String getProjectPage(Model model) {
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);
		return "/backend/ProjectCreate";
	}
	
	@Autowired
	@Qualifier("projectdaomysql")
	private ProjectDao projectDao;
	
	@Autowired
	@Qualifier("projectmemberdaomysql")
	private ProjectMemberDao projectMemberDao;
	
	//新增專案
	@RequestMapping(value = "/addproject", method = {RequestMethod.GET,RequestMethod.POST},produces = "text/plain;charset=utf-8")
	@ResponseBody
	
	//
	public String addProject(@RequestParam(name = "projectId")String projectId,
							 @RequestParam(name = "projectName")String projectName,
							 @RequestParam(name = "projectContent")String projectContent,
							 @RequestParam(name = "projectOwner")String projectOwner,
							 @RequestParam(name = "projectMember")List<Employee> projectMember,
							 @RequestParam(name = "projectStartDate")Date projectStartDate,
							 @RequestParam(name = "projectEndDate")Date projectEndDate) throws ParseException{
		
		Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectName(projectName);
		project.setProjectContent(projectContent);
		project.setProjectOwner(projectOwner);
		project.setProjectMembers(projectMember);
		project.setProjectStartDate(projectStartDate);
		project.setProjectEndDate(projectEndDate);
		
		try {
			int rowcount = projectDao.addProject(project);
			if (rowcount ==0) {
				return "專案新增失敗";
			}else {
				return "專案新增成功";
			}
		} catch (Exception e) {
			return "專案新增失敗:" + (e.getMessage().contains("Duplicate entry") ? "此專案已新增" : e.getMessage());
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
	
	//查看所有專案成員
	@GetMapping(value = "/viewprojectmembers",produces = "text/plain;charest=utf-8")
	@ResponseBody
	public String findAllProjectMembers(Model model) {
		List<ProjectMember> projectMembers = projectMemberDao.findAllProjectMembers();
		model.addAttribute("projectMembers",projectMembers);
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
	
	

	
	
	
	