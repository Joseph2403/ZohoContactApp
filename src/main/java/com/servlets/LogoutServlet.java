package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.SessionManager;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String sessionId = SessionManager.getSessionIdFromCookies(request);
		try {
			SessionManager.deleteSessionFromDb(sessionId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        SessionManager.deleteSessionFromCookies(response);
        SessionManager.delExpiry();
        System.out.println("EXPIRED AT (LOGOUT) = "+SessionManager.expiredAt);
        response.sendRedirect("login.jsp");
    }
}
