package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.SessionManager;
import com.database.UserDao;

@WebServlet("/EditUserDetailsServlet")
public class EditUserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String sessionId = SessionManager.getSessionIdFromCookies(request);
		Long userId = SessionManager.getUserId(sessionId);
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String dateOfBirth = request.getParameter("dateOfBirth");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		try {
			if (UserDao.editUserDetails(userId, name, age, dateOfBirth, state, city)) {
				response.sendRedirect("userdashboard.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Could not edit details!!!');");
                out.println("window.location.href = 'userdashboard.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
