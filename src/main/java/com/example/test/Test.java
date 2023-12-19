package com.example.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.bean.Project;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectDaoImplMySQL;

public class Test {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProjectDao projectDao = ctx.getBean("projectdaomysql", ProjectDaoImplMySQL.class);
		
		List<Project> projects = projectDao.findAllProjects();

		System.out.println(projects);
	}

}
