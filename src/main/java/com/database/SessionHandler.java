package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pojo.Session;
import com.querylayer.Executor;

public class SessionHandler {
	private static HashMap<String, Session> sessionCache = new HashMap<>();

	public static HashMap<String, Session> getSessionCache() {
		return sessionCache;
	}

	public static void addToSessionCache(Session session) {
		sessionCache.put(session.getSessionId(), session);
		System.out.println("Session expires at " + getFormatedUTCTime(session.getExpiresAt()));
		System.out.println(sessionCache.get(session.getSessionId()).getSessionId() + "\n\n");
	}

	public static void updateSessionCache(String sessionId) {
		Session session = getSessionDetails(sessionId);
		long currentTime = System.currentTimeMillis();
		long expiresAt = currentTime + (30 * 60 * 1000);
		session.setLastAccessed(currentTime);
		session.setExpiresAt(expiresAt);
		sessionCache.put(sessionId, session);
		System.out.println("Session expires at " + getFormatedUTCTime(expiresAt));
		System.out.println(sessionCache.get(sessionId).getSessionId() + "\n\n");
	}

	public static void deleteSessionCache(String sessionId) {
		sessionCache.remove(sessionId);
		System.out.println("SESSION EXPIRED!!!");
		Session session = sessionCache.get(sessionId);
		if (session != null) {

			System.out.println(sessionCache.get(sessionId).getSessionId() + "\n\n");
		} else {
			System.out.println("EMPTY SESSION" + "\n\n");
		}
	}

	public static void loadActiveSessions() {
		String query = "select * from Session where expiresAt > ?;";
		try (Connection conn = Executor.connectToDB(); PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setLong(1, System.currentTimeMillis());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Session session = new Session();
				session.setSessionId(query);
				session.setSessionId(rs.getString(1));
				session.setUserId(rs.getLong(2));
				session.setCreatedAt(rs.getLong(3));
				session.setLastAccessed(rs.getLong(4));
				session.setExpiresAt(rs.getLong(5));
				sessionCache.put(session.getSessionId(), session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getFormatedUTCTime(long currentTime) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = dateTime.format(formatter);
		return formattedDateTime;
	}

	public static String createSession(long userId) {
		String sessionId = UUID.randomUUID().toString();
		long currentTime = System.currentTimeMillis();
		long lastUpdatedTime = currentTime;
		long expiredAt = lastUpdatedTime + (30 * 60 * 1000);
		String query = "insert into Session values (?, ?, ?, ?, ?);";
		try (Connection conn = Executor.connectToDB(); PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, sessionId);
			ps.setLong(2, userId);
			ps.setLong(3, currentTime);
			ps.setLong(4, lastUpdatedTime);
			ps.setLong(5, expiredAt);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				return sessionId;
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		if (sessionCache.containsKey(sessionId)) {
			return sessionCache.get(sessionId);
		}
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

	public static Session getNewSession(Long userId) {
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

	public static boolean deleteSessionFromDb(String sessionId) {
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

}
