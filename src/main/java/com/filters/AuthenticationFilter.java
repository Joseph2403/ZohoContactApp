package com.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.database.SessionManager;
import com.pojo.Session;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		Session session;
		try {
			if (uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp") || uri.endsWith("/signup.jsp")
					|| uri.endsWith("/LoginServlet") || uri.endsWith("/SignUpServlet")) {
				session = SessionManager.getSessionFromCookies(httpRequest);
				if (session != null) {
					httpResponse.sendRedirect("userdashboard.jsp");
				}
				else {
					chain.doFilter(httpRequest, httpResponse);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
