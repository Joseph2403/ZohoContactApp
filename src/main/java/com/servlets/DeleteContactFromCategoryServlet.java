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

import com.database.CategoryDao;

@WebServlet("/DeleteContactFromCategoryServlet")
public class DeleteContactFromCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		long contactId = Long.parseLong(request.getParameter("deleteId"));
		long categoryId = (long) session.getAttribute("categoryId");
		String categoryName = (String) session.getAttribute("categoryName");
		String path = "viewcategory.jsp?categoryId="+categoryId+"&categoryName="+categoryName;
		try {
			if (CategoryDao.deleteContactFromCategory(categoryId, contactId)) {
				response.sendRedirect(path);
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Group was not Created!!!');");
                out.println("window.location.href = "+path+";");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
