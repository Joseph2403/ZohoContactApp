package com.servlets;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.ContactDao;

@WebServlet("/ViewCategoryServlet")
public class ViewCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		long categoryId = Long.parseLong(request.getParameter("categoryId"));
		long userId = (long) session.getAttribute("userId");

		/*
		 * <% while (rs.next()) { %> <input type="checkbox"
		 * id="<%= rs.getLong("contactId") %>" name="fav"
		 * value="<%= rs.getLong("contactId") %>" > <label
		 * for="<%= rs.getLong("contactId") %>"><%= rs.getString("name") %></label><br>
		 * <% } %>
		 */
		
	}

}
