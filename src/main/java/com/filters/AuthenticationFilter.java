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


@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter extends HttpFilter implements Filter {
       

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		
		Long userId;
		String sessionId;
		
		
		if (uri.endsWith("LoginServlet") || uri.endsWith("SignUpServlet")) {
			sessionId = SessionManager.getSessionId(httpRequest);
			if (sessionId != null) {
				try {
					userId = SessionManager.getUserId(sessionId);
					if (userId != null) {						
						httpResponse.sendRedirect("userdashboard.jsp");
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
            chain.doFilter(request, response); // Skip the filter
            return;
        }
		
		sessionId = SessionManager.getSessionId(httpRequest);
		if (sessionId != null) {
			try {
				userId = SessionManager.getUserId(sessionId);
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		HttpSession session = httpRequest.getSession(false);
		if (session.getAttribute("userId") != null) {
			chain.doFilter(request, response);
		}
		else {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
		}
		
	}


}