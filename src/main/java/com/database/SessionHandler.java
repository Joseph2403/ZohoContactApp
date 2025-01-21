package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pojo.Session;
import com.querylayer.Executor;

public class SessionHandler {
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

	public static Session getSessionDetails(String sessionId) {
		Session session = null;
		String query = "select * from Session where sessionId = ?;";
		try (Connection conn = Executor.connectToDB(); PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, sessionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				session = new Session();
				session.setSessionId(rs.getString(1));
				session.setUserId(rs.getLong(2));
				session.setCreatedAt(rs.getLong(3));
				session.setLastAccessed(rs.getLong(4));
				session.setExpiresAt(rs.getLong(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public static Session getNewSession(Long userId) throws ClassNotFoundException, SQLException {
		Session session = null;
		String sessionId = createSession(userId);
		if (sessionId != null) {
			return getSessionDetails(sessionId);
		}
		return session;
	}

	public static void storeSessionIdInCookies(HttpServletResponse response, String sessionId) {
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

	public static boolean updateSessionInDb(String sessionId) {
		String query = "update Session set lastAccessed = ?, expiresAt = ? where sessionId = ?;";
		long time = System.currentTimeMillis();
		try (Connection conn = Executor.connectToDB(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setLong(1, time);
			stmt.setLong(2, time + (30 * 60 * 1000));
			stmt.setString(3, sessionId);
			int rs = stmt.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
	
	
}
