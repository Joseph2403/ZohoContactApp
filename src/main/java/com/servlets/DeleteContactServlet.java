package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.ContactDao;

@WebServlet("/DeleteContactServlet")
public class DeleteContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long contactId = Long.parseLong(request.getParameter("deleteId"));
		try {
			if (ContactDao.deleteContact(contactId)) {
				response.sendRedirect("contacts.jsp");
			}
			else {
				out.println("<script type=\"text/javascript\">");
                out.println("alert('Unable to delete contact!!!');");
                out.println("window.location.href = 'contacts.jsp';");
                out.println("</script>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
