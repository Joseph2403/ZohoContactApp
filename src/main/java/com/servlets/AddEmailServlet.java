package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.UserDao;

@WebServlet("/AddEmailServlet")
public class AddEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddEmailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        HttpSession session =request.getSession();
        long userId = (long) session.getAttribute("userId");
        String mailId = request.getParameter("addemail");
        try {
            if(UserDao.insertUserEmail(userId, mailId, false)) {
            	response.sendRedirect("userdashboard.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
