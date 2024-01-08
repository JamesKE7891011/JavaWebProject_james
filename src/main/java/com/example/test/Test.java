package com.example.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.dao.ProjectDao;
import com.example.dao.ProjectDaoImplMySQL;

public class Test {

	public static void main(String[] args) throws SQLException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/test.xml");
		
		ProjectDao projectDaoImplMySQL = ctx.getBean("projectdaomysql", ProjectDaoImplMySQL.class);
		System.out.println(projectDaoImplMySQL.findAllProjects());
	}

}
