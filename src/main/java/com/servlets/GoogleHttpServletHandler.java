package com.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/GoogleHttpServletHandler")
public class GoogleHttpServletHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");

		if (code != null) {
			String accessToken = getAccessToken(code);
			String userProfile = getUserData(accessToken);

			JSONObject result = new JSONObject();
			result.put("userProfile", new JSONObject(userProfile));

			response.setContentType("application/json");
			response.getWriter().write(result.toString());
		} else {
			response.getWriter().println("Authorization code not found!");
		}
	}

	private String getAccessToken(String code) throws IOException {
		String tokenUrl = "https://oauth2.googleapis.com/token";
		String clientId = "1027644263955-l6f6p81imsk9f83kl4n3oi0t7bp4quse.apps.googleusercontent.com";
		String clientSecret = "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";
		String redirectUri = "http://localhost:8080/ContactApp/GoogleHttpServletHandler";

		String postData = "code=" + code + "&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&redirect_uri=" + redirectUri + "&grant_type=authorization_code";

		URL url = new URL(tokenUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.getOutputStream().write(postData.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder responseBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			responseBuilder.append(line);
		}

		JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
		return jsonResponse.getString("access_token");
	}

	private String getUserData(String accessToken) throws IOException {
		String url = "https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses";

		URL apiUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder responseBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			responseBuilder.append(line);
		}

		return responseBuilder.toString();
	}
}
