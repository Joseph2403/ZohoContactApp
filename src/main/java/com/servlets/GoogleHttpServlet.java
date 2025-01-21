package com.servlets;

import java.io.IOException;
//import java.io.PrintWriter;'
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GoogleHttpServlet")
public class GoogleHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("GoogleHttpServlet initialized");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		PrintWriter out = response.getWriter();
		System.out.println("GoogleHttpServlet doGet() invoked");

		String clientId = "1027644263955-l6f6p81imsk9f83kl4n3oi0t7bp4quse.apps.googleusercontent.com";
		String redirectUri = "http://localhost:8080/ContactApp/GoogleHttpServletHandler";
		String scope = "email profile https://www.googleapis.com/auth/contacts.readonly"; // Added contacts.readonly
																							// scope
		String state = "qwertyuiopasdfghjklzxcvbnm";

		String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" + "?client_id=" + clientId + "&redirect_uri="
				+ URLEncoder.encode(redirectUri, "UTF-8") + "&response_type=code" + "&scope="
				+ URLEncoder.encode(scope, "UTF-8") + "&state=" + URLEncoder.encode(state, "UTF-8");

		System.out.println(authUrl);
		response.sendRedirect(authUrl);
	}
}
