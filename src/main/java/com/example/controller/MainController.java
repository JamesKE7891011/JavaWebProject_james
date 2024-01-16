package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bean.Employee;
import com.example.bean.Issue;
import com.example.bean.Project;
import com.example.bean.ProjectMember;
import com.example.dao.EmployeeDaoimplMySQL;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectMemberDao;

@Controller
@RequestMapping("/main")
public class MainController {
	
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
	public String getMainPage(Model model) {
		
		// 1. projects
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);

		System.out.println(projects);
				
		// 2. members
		List<Employee> employees = employeedao.findAllEmployees();
		model.addAttribute("employees", employees);
		
		return "/frontend/Main";
	}
	
	@GetMapping(value = "findproject/{projectId}")
	@ResponseBody
	public Project findProjectByProjectId(@PathVariable("projectId") String projectId){
		return projectDao.findProjectById(projectId).get();
	}
	
		
}





