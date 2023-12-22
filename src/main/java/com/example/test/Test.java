package com.example.test;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.bean.Project;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectDaoImplMySQL;

public class Test {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProjectDao projectDao = ctx.getBean("projectdaomysql", ProjectDaoImplMySQL.class);
		
//		List<Project> projects = projectDao.findAllProjects();
//		System.out.println(projects);
		
		Optional<Project> prOptional = projectDao.findProjectById("AC23020");
		prOptional.ifPresent(System.out::println);
		
//		Project project = prOptional.get();
//		project.setProjectName("SleepingBeauty");
//		projectDao.updateProject(project);
	}

}
