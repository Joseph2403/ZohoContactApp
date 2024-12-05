package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.CategoryDao;

@WebServlet("/DeleteCategoryServlet")
public class DeleteCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		long categoryId = Long.parseLong(request.getParameter("categoryId"));
		try {
			if (CategoryDao.deleteCategory(categoryId)) {
				response.sendRedirect("userdashboard.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Could not delete Category!!!');");
                out.println("window.location.href = 'login.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace(response.getWriter());
		}
	}

}
