package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.SessionManager;
import com.database.UserDao;

@WebServlet("/AddPhoneServlet")
public class AddPhoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String sessionId = SessionManager.getSessionIdFromCookies(request);
		Long userId = SessionManager.getUserId(sessionId);
        long phone = Long.parseLong(request.getParameter("addphone"));
        try {
            if(UserDao.insertUserPhone(userId, phone)) {
            	response.sendRedirect("userdashboard.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
