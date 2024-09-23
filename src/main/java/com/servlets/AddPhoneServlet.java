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

@WebServlet("/AddPhoneServlet")
public class AddPhoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddPhoneServlet() {
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
        long phone = Long.parseLong(request.getParameter("addphone"));
        try {
            if(UserDao.insertUserPhone(userId, phone)) {
            	out.println("<html><body>");
                out.println("<p>Phone Number Linked Successfully</p>");
            	out.println("<a href='userdashboard.jsp'>Dashboard</a>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
