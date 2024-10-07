package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.ContactDao;

@WebServlet("/AddContactServlet")
public class AddContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddContactServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		long userId = (long) session.getAttribute("userId");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("contactEmail1");
        String name = request.getParameter("contName");
        String dateOfBirth = request.getParameter("contDateOfBirth");
        int age = Integer.parseInt(request.getParameter("contAge"));
        String state = request.getParameter("contState");
        String city = request.getParameter("contCity");
        long phoneNumber = Long.parseLong(request.getParameter("contactPhone1"));
        try {
        	long contactId = ContactDao.insertContact(userId, name, age, dateOfBirth, state, city);
			if (ContactDao.insertContactEmail(contactId, email) && ContactDao.insertContactPhone(contactId, phoneNumber)) {
				out.println("<p>Contact Add Aiduchu: Contact No - "+contactId+"</p>");
				out.println("<a href='userdashboard.jsp'>Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(response.getWriter()));
		}
	}

}
