package com.example.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.bean.Issue;
import com.example.dao.IssueClassDao;
import com.example.dao.IssueDao;
import com.example.dao.ProjectDao;
import com.example.dao.ProjectDaoImplMySQL;

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
	
		
		IssueDao issueDaoImplMySQL = ctx.getBean("issuedaomysql", IssueDao.class);
		System.out.println(issueDaoImplMySQL.removeIssueById(1));

	}

}
