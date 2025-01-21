package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.database.*;
@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String categoryName = request.getParameter("categoryName");
		if (categoryName.equals("")) {
			response.sendRedirect("userdashboard.jsp");
		}
		else {
			String sessionId = SessionManager.getSessionIdFromCookies(request);
			Long userId = SessionManager.getUserId(sessionId);
		try {
			Connection conn = UserDao.connectToDB();
			String query = "insert into Category (userId, categoryName) values (?, ?);";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setString(2, categoryName);
			int rs = ps.executeUpdate();
			if(rs > 0) {
				response.sendRedirect("userdashboard.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Group was not Created!!!');");
                out.println("window.location.href = 'userdashboard.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		}
	}

}
