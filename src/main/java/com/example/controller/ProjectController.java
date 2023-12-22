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
	public String addProject(@RequestParam(name = "project_id")String project_id,
							 @RequestParam(name = "project_name")String project_name,
							 @RequestParam(name = "project_content")String project_content,
							 @RequestParam(name = "project_owner")String project_owner,
							 @RequestParam(name = "project_member")List<Employee> project_member,
							 @RequestParam(name = "project_start")Date project_start,
							 @RequestParam(name = "project_end")Date project_end) throws ParseException{
		
		Project project = new Project();
		project.setProjectId(project_id);
		project.setProjectName(project_name);
		project.setContent(project_content);
		project.setOwner(project_owner);
		project.setMembers(project_member);
		project.setStartDate(project_start);
		project.setEndDate(project_end);
		
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
	    int rowcount = projectDao.cancelProject(projectId);
	    return rowcount == 0 ? "專案取消失敗" : "專案取消成功";
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
		List<Employee> projectMembers = projectMemberDao.findAllProjectMembers();
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
		projectUpdate.setContent(newcontent);
		projectUpdate.setMembers(newmembers);
		projectUpdate.setStartDate(newstartDate);
		projectUpdate.setEndDate(newendDate);
		
	    return projectDao.updateProject(projectUpdate) == 0 ? "專案修改失敗" : "專案修改成功";
	}
	
	
	
}
	
	

	
	
	
	