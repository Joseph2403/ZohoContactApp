package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pojo.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.SessionHandler;
import com.database.SessionManager;
import com.database.UserDao;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		char[] password = request.getParameter("password").toCharArray();
		String name = request.getParameter("name");
		String dateOfBirth = request.getParameter("dateOfBirth");
		int age = Integer.parseInt(request.getParameter("age"));
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));

		Argon2 argon2 = Argon2Factory.create();
		String hashedPassword = argon2.hash(10, 65535, 1, password);

		try {
			if (UserDao.checkExistingEmail(email)) {
				out.println("Email ID already Existing");
			} else {
				User user = new User(hashedPassword, name, dateOfBirth, age, state, city, 1);
				ArrayList<UserEmail> emails = new ArrayList<>();
				long userId = UserDao.insertUser(user);
				UserEmail userEmail = new UserEmail(userId, email, true);
				emails.add(userEmail);
				if (UserDao.insertUserEmail(userId, email, true) && UserDao.insertUserPhone(userId, phoneNumber)) {
					Session session = SessionHandler.getNewSession(user.getUserId());
					SessionHandler.storeSessionIdInCookies(response, session.getSessionId());
					SessionHandler.addToSessionCache(session);
					user.setUserId(userId);
					user.setUserEmail(emails);
					response.sendRedirect("userdashboard.jsp");
				} else {
					out.println("<script type=\"text/javascript\">");
					out.println(
							"alert('There was some kind of error in tne Email or the Phonenumber that your have entered!!!');");
					out.println("window.location.href = 'signup.jsp'");
					out.println("</script>");
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(response.getWriter());
		} finally {
			argon2.wipeArray(password);
		}
	}

}
