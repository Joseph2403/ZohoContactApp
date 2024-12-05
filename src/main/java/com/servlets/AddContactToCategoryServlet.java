package com.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.database.*;

@WebServlet("/AddContactToCategoryServlet")
public class AddContactToCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		long categoryId = (long) session.getAttribute("categoryId");
		String[] contactIds = request.getParameterValues("cat");
		String path = "viewcategory.jsp?categoryId="+session.getAttribute("categoryId")+"&categoryName="+session.getAttribute("categoryName");
		try {
			if (CategoryDao.addContactsToCategory(categoryId, contactIds)) {
				response.sendRedirect(path);
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Contacts are not added to this Category!!!');");
                out.println("window.location.href = "+path);
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
