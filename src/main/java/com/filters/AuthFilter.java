package com.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.database.SessionHandler;
import com.database.SessionManager;
import com.pojo.Session;

@WebFilter("/AuthFilter")
public class AuthFilter extends HttpFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURI();
		String sessionId = SessionHandler.getSessionIdFromCookies(httpRequest);
		boolean isValidSession = SessionHandler.getSessionDetails(sessionId) != null ? true : false;
		boolean isInitialServlet = uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp")
				|| uri.endsWith("/signup.jsp") || uri.endsWith("/LoginServlet") || uri.endsWith("/SignUpServlet")
				|| uri.endsWith("/");

		if (!isValidSession && isInitialServlet) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}

		if (!isValidSession && !isInitialServlet) {
			httpResponse.sendRedirect("login.jsp");
			return;
		}

		if (isValidSession && !isInitialServlet) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}

		if (isValidSession && isInitialServlet) {
			httpResponse.sendRedirect("userdashboard.jsp");
			return;
		}

		chain.doFilter(httpRequest, httpResponse);

	}

}