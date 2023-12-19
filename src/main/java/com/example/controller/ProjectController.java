package com.example.controller;

import java.sql.Date;

import java.util.List;

import javax.ws.rs.DELETE;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bean.Project;
import com.example.dao.ProjectDao;


@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@GetMapping
	public String getProjectPage() {
		return "/backend/ProjectCreate";
	}
	
	@Autowired()
	@Qualifier("projectDaoMySQL")
	private ProjectDao projectDao;
	
	//新增專案
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST},produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String addProject(@RequestParam(name = "projectId")String projectId,
							 @RequestParam(name = "projectName")String projectName,
							 @RequestParam(name = "content")String content,
							 @RequestParam(name = "owner")String owner,
							 @RequestParam(name = "members")List members,
							 @RequestParam(name = "startDate")Date startDate,
							 @RequestParam(name = "endDate")Date endDate) throws ParseException{
		
		Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectName(projectName);
		project.setContent(content);
		project.setOwner(owner);
		project.setMembers(members);
		project.setStartDate(startDate);
		project.setEndDate(endDate);
		
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
//	
//	//取消專案
//	@DeleteMapping(produces = "text/plain;charset=utf-8")
//	@ResponseBody
//	public String cancelProject(@PathVariable("projectId")String projectId) {
//		int rowcount = projectDao.cancelProject(projectId);
//		if (rowcount ==0) {
//			return "專案新增失敗";
//		}else {
//			return "專案新增成功";
//		}
//	}
//	
//	//查看所有專案內容
//	@GetMapping(produces = "text/plain;charset=utf-8")
//	@ResponseBody
//	public String findAllProjects(Model model) {
//		List<Project> projects = projectDao.findAllProjects();
//		model.addAttribute("projects", projects);
//		return "Project/listMySQL";
//	}
//	
//	//修改專案內容
//	@RequestMapping( method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain;charset=utf-8")
//	@ResponseBody
//	public String updateProject(@PathVariable("projectId") String projectId,
//								@RequestParam(name = "projectName")String newprojectName,
//								@RequestParam(name = "content")String newcontent,
//								@RequestParam(name = "owner")String newowner,
//								@RequestParam(name = "members")String newmembers,
//								@RequestParam(name = "startDate")Date newstartDate,
//								@RequestParam(name = "endDate")Date newendDate){
//		//int rowcount = ProjectDao.updateProject(String projectId, String newprojectName, String newcontent, String newowner, List<String> newmembers,
//				//Date newstartDate, Date newendDate);
//		//return rowcount == 0 ? "專案修改失敗" : "專案修改成功";
//						
//		return "";
//	}
//	
}
	
	

	
	
	
	