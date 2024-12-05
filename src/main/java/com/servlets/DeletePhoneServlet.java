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

import com.database.UserDao;


@WebServlet("/DeletePhoneServlet")
public class DeletePhoneServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		long userId = (long) session.getAttribute("userId");
		long phoneNumber = Long.parseLong(request.getParameter("delPhone"));
		try {
			if (UserDao.countUserPhone(userId) > 1) {
			if (UserDao.deleteUserPhone(userId, phoneNumber)) {
				response.sendRedirect("userdashboard.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Some error occured!!!');");
                out.println("window.location.href = 'userdashboard.jsp';");
                out.println("</script>");
			}
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('You cannot delete all the numbers!!!');");
                out.println("window.location.href = 'userdashboard.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
