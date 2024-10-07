package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.database.UserDao;


@WebServlet("/DeleteEmailServlet")
public class DeleteEmailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String mailId = request.getParameter("delEmail");
		try {
			if (UserDao.deleteUserEmail(mailId)) {
				response.sendRedirect("userdashboard.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Cannot Delete Primary Email ID!!!');");
                out.println("window.location.href = 'userdashboard.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
