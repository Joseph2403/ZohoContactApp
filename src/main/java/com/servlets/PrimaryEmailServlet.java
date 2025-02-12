package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.SessionManager;
import com.database.UserDao;

@WebServlet("/PrimaryEmailServlet")
public class PrimaryEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mailId = request.getParameter("primaryEmail");
		String sessionId = SessionManager.getSessionIdFromCookies(request);
		Long userId = SessionManager.getUserId(sessionId);
		try {
			if (UserDao.changePrimaryEmail(mailId, userId)) {
				response.sendRedirect("userdashboard.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
