package com.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.database.SessionManager;
import com.pojo.Session;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter extends HttpFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURI();
		Session session;

		try {
			if (uri.endsWith("/GoogleHttpServlet") || uri.endsWith("GoogleHttpServletHandler")) {
				chain.doFilter(httpRequest, httpResponse);
				return;
			}
			if (uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp") || uri.endsWith("/signup.jsp")
					|| uri.endsWith("/LoginServlet") || uri.endsWith("/SignUpServlet") || uri.endsWith("/")) {
				if (SessionManager.expiredAt != null) {
					if (!SessionManager.validateExpiry()) {
						SessionManager.deleteSessionFromCookies(httpResponse);
						chain.doFilter(httpRequest, httpResponse);
						return;
					}
				}
				SessionManager.updateExpiry();
				session = SessionManager.getSessionFromCookies(httpRequest);

				if (session != null) {
					httpResponse.sendRedirect("userdashboard.jsp");
					return;
				} else {
					chain.doFilter(httpRequest, httpResponse);
					return;
				}
			}

			if (!SessionManager.validateExpiry()) {
				String sessionId = SessionManager.getSessionIdFromCookies(httpRequest);
				SessionManager.deleteSessionFromDb(sessionId);
				SessionManager.deleteSessionFromCookies(httpResponse);
				httpResponse.sendRedirect("login.jsp");
				return;
			}
			SessionManager.updateExpiry();

			session = SessionManager.getSessionFromCookies(httpRequest);
			if (session == null) {
				httpResponse.sendRedirect("login.jsp");
				return;
			}

			chain.doFilter(httpRequest, httpResponse);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
