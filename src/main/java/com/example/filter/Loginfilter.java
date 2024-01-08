package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(value={"/*"})
public class Loginfilter extends HttpFilter{

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String urlString = req.getRequestURL().toString();
		
		//放行條件
		if(urlString.endsWith("login.jsp") || urlString.endsWith("login") 
				|| urlString.indexOf("/images")>=0 
				|| urlString.endsWith(".css")
				|| urlString.endsWith(".js") 
				|| urlString.indexOf("/upload")>=0 ) {
			chain.doFilter(req, resp);
			return;
		}
		//檢查是否登入
		HttpSession session = req.getSession();
		boolean isLogin = session.getAttribute("isLogin") == null ? false: (boolean)session.getAttribute("isLogin");
		if(!isLogin) {
			resp.sendRedirect("/JavaWebProject_james/login.jsp");
			return;
		}
		
		chain.doFilter(req, resp);
	}
	
}
