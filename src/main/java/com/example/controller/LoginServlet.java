package com.example.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// 預設用戶 username = user，password = 123
		boolean isPasswordMatch = BCrypt.checkpw(password,"$2a$10$SokCMdV9tI3F0bM3C6NnG.RpvH6RWzRB9eLkZjahmvm0NMr56aN42");
		
		if ("user".equals(username) && isPasswordMatch) {
			HttpSession session = req.getSession();
			session.setAttribute("isLogin", true);
			session.setAttribute("username", username);
			session.setAttribute("role", "user");
			resp.sendRedirect("./frontend/Main.jsp");
			return;
		}
		
		if ("admin".equals(username) && isPasswordMatch) {
			HttpSession session = req.getSession();
			session.setAttribute("isLogin", true);
			session.setAttribute("username", username);
			session.setAttribute("role", "admin");
			resp.sendRedirect("./frontend/Main.jsp");
			return;
		}

		// 驗證沒過的時候，如和處理
		req.setAttribute("error", "帳號密碼輸入錯誤!");
		RequestDispatcher rd = req.getRequestDispatcher("./login.jsp");
		rd.forward(req, resp);
	}
}
