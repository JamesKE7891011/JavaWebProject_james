package com.example.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.bean.Issue;
import com.example.dao.IssueClassDao;
import com.example.dao.IssueDao;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectDaoImplMySQL;
import com.google.gson.Gson;

//public class Test {
//
//	public static void main(String[] args) throws SQLException {
//		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/test.xml");
//		
//		String issueClassId = "B";
//		
//		IssueClassDao issueClassDaoImplMySQL = ctx.getBean("issueclassdaomysql", IssueClassDao.class);
//		System.out.println(issueClassDaoImplMySQL.findIssueClassById("B"));
//	}
//
//}

public class Test {

	public static void main(String[] args) throws SQLException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/test.xml");
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		IssueDao issueDao = ctx.getBean("issuedaomysql", IssueDao.class);
		
//		Issue i = new Issue();
//		i.setProjectId("AC23020");
//		i.setIssueName("Test");
//		i.setIssueClassId("D");
//		i.setIssueStatus(1);
//		i.setIssueContent("Test Content");
//		issueDao.addIssue(i);
		
		List<Issue> issues = issueDao.findIssuesByProjectId("AC23020");
		for(Issue issue: issues) {
			System.out.println(issue);
		}

	}

}
