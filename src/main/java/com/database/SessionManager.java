package com.database;

import java.util.UUID;

import javax.servlet.http.*;
import java.sql.*;

import com.pojo.Session;
import com.querylayer.*;

public class SessionManager {
	public static Long expiredAt = null;

	public static void updateExpiry() {
		expiredAt = System.currentTimeMillis() + (30 * 60 * 1000);
	}

	public static boolean validateExpiry() {
		return System.currentTimeMillis() < expiredAt;
	}

	public static void delExpiry() {
		expiredAt = null;
	}

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

	public static Session getSessionFromCookies(HttpServletRequest request)
			throws ClassNotFoundException, SQLException {
		Session session = null;
		String sessionId = getSessionIdFromCookies(request);
		if (sessionId != null) {
			session = new Session();
			ResultSet rs = getSessionDetails(sessionId);
			session.setSessionId(rs.getString(1));
			session.setUserId(rs.getLong(2));
			session.setCreatedAt(rs.getLong(3));
			session.setLastAccessed(rs.getLong(4));
			session.setExpiresAt(rs.getLong(5));
		}
		return session;
	}

	public static Session getNewSession(Long userId) throws ClassNotFoundException, SQLException {
		Session session = null;
		String sessionId = createSession(userId);
		if (sessionId != null) {
			session = new Session();
			ResultSet rs = getSessionDetails(sessionId);
			session.setSessionId(rs.getString(1));
			session.setUserId(rs.getLong(2));
			session.setCreatedAt(rs.getLong(3));
			session.setLastAccessed(rs.getLong(4));
			session.setExpiresAt(rs.getLong(5));
		}
		return session;
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

	public static void deleteSessionFromCookies(HttpServletResponse response) {
		Cookie ck = new Cookie("sessionId", "");
		ck.setMaxAge(0);
		response.addCookie(ck);
	}

	public static boolean deleteSessionFromDb(String sessionId) throws ClassNotFoundException {
		String query = "DELETE FROM Session WHERE sessionId = ?";
		try (Connection conn = Executor.connectToDB(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, sessionId);
			int rs = stmt.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getSessionIdFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("sessionId")) {
					return cookies[i].getValue();
				}
			}
		}

		return null;
	}

	public static Long getUserId(String sessionId){
		String query = "select userId from Session where sessionId = ?;";
		try (Connection conn = Executor.connectToDB(); PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, sessionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong("userId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet getSessionDetails(String sessionId) throws ClassNotFoundException, SQLException {
		String query = "select * from Session where sessionId = ?;";
		Connection conn = Executor.connectToDB();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, sessionId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs;
		}
		return null;
	}

}
