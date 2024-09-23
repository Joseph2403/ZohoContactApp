package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.UserDao;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SignUpServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        int age = Integer.parseInt(request.getParameter("age"));
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
        try {
        	if (UserDao.checkExistingEmail(email)) {
        		out.println("Email ID already Existing");
        	}
        	else {
        		long userId = UserDao.insertUser(password, name, dateOfBirth, age, state, city);
    			if (UserDao.insertUserEmail(userId, email) && UserDao.insertUserPhone(userId, phoneNumber)) {
    				out.println("Add aiduchu: User No - "+userId);
    			}
        	}
 			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			out.println("Code a Check panra");
		}
	}

}
