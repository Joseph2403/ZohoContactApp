package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.SessionHandler;
import com.database.SessionManager;
import com.database.UserDao;
import com.pojo.Session;
import com.pojo.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			User user = UserDao.loginUser(email, password);
			if (user != null) {
				Session session = SessionHandler.getNewSession(user.getUserId());
				SessionHandler.storeSessionIdInCookies(response, session.getSessionId());
				response.sendRedirect("userdashboard.jsp");
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid User!!!');");
				out.println("window.location.href = 'login.jsp';");
				out.println("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());

		}
	}

}
