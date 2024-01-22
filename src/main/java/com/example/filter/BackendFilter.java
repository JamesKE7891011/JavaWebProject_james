//package com.example.filter;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebFilter(value = "/mvc/project")
//public class BackendFilter extends HttpFilter{
//
//	@Override
//	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//
//		HttpSession session = req.getSession();
//		
//		String role = (String)session.getAttribute("role");
//		
//		if(role != null && "admin".equalsIgnoreCase(role)) {
//			res.sendRedirect("/JavaWebProject_james/ProjectCreate.jsp");
//			return;
//		}
//		
//		res.sendRedirect("/JavaWebProject_james/fwbackendpage.jsp");
//	}
//
//	
//}
