package com.database;

import java.util.UUID;

import javax.servlet.http.*;
import java.sql.*;
import com.querylayer.*;

public class SessionManager {
	public static String createSession(long userId) throws ClassNotFoundException, SQLException {
		String sessionId = UUID.randomUUID().toString();
		long currentTime = System.currentTimeMillis();
		long lastUpdatedTime = currentTime;
		long expiredAt = lastUpdatedTime + (30 * 60 * 1000);
		String query = "insert into Session values (?, ?, ?, ?, ?);";
		Connection conn = Executor.connectToDB();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, sessionId);
		ps.setLong(2, userId);
		ps.setLong(3, currentTime);
		ps.setLong(4, lastUpdatedTime);
		ps.setLong(5, expiredAt);
		int rs = ps.executeUpdate();
		if (rs > 0) {			
			return sessionId;
		}
		return null;
	}

	public static boolean validateSession(String sessionId) throws ClassNotFoundException {
		String query = "SELECT expiresAt FROM sessions WHERE sessionId = ?";
		try (Connection conn = Executor.connectToDB(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, sessionId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					long expiresAt = rs.getLong("expiresAt");
					return System.currentTimeMillis() < expiresAt;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void storeSessionId(HttpServletResponse response, String sessionId) {
		Cookie ck = new Cookie("sessionId", sessionId);
		response.addCookie(ck);
	}

	public static String getSessionId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("sessionId")) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	public static Long getUserId(String sessionId) throws ClassNotFoundException, SQLException {
		String query = "select userId from Session where sessionId = ?;";
		Connection conn = Executor.connectToDB();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, sessionId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getLong("userId");
		}
		return null;
	}


}
