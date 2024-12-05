package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.ContactDao;


@WebServlet("/DisplayContactDetailsServlet")
public class DisplayContactDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayContactDetailsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		long contactId =  Long.parseLong(request.getParameter("contactId"));
		request.setAttribute("contactId", contactId);
		RequestDispatcher rp = request.getRequestDispatcher("contactdetails.jsp");
		rp.forward(request, response);
//		response.sendRedirect("contactdetails.jsp");
	}

}
