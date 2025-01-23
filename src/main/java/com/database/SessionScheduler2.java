package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.catalina.webresources.war.Handler;

import com.pojo.Session;
import com.querylayer.Executor;

@WebListener
public class SessionScheduler2 implements ServletContextListener {
	ScheduledExecutorService scheduler;

	public SessionScheduler2() {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		flushCacheToDatabase();
		if (scheduler != null) {
			scheduler.shutdown();
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Session Scheduler Initialized");
		SessionHandler.loadActiveSessions();
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			flushCacheToDatabase();
			clearExpiredSessions();
		}, 0, 5, TimeUnit.DAYS.SECONDS);
	}

	public void clearExpiredSessions() {
		String deleteExpiredSessionsQuery = "DELETE FROM Session WHERE expiresAt < ?;";
		try (Connection connection = Executor.connectToDB();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteExpiredSessionsQuery)) {

			long currentTimeMillis = System.currentTimeMillis();
			preparedStatement.setLong(1, currentTimeMillis);

			int rowsDeleted = preparedStatement.executeUpdate();
//			System.out.println("Deleted " + rowsDeleted + " expired sessions from the database.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void flushCacheToDatabase() {
//		System.out.println("Flushing session cache to the database...");
		HashMap<String, Session> sessionCache = SessionHandler.getSessionCache();

		for (Session session : sessionCache.values()) {
			boolean sessionExists = checkIfSessionExists(session.getSessionId());

			if (sessionExists) {
				updateSessionInDatabase(session);
			} else {
				insertSessionIntoDatabase(session);
			}
		}

//		System.out.println("Flushed " + sessionCache.size() + " sessions from cache to the database.");
	}

	private boolean checkIfSessionExists(String sessionId) {
		String query = "SELECT 1 FROM Session WHERE sessionId = ?";
		try (Connection connection = Executor.connectToDB();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, sessionId);
			return preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void insertSessionIntoDatabase(Session session) {
		String query = "INSERT INTO Session (sessionId, userId, createdAt, lastAccessed, expiresAt) VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = Executor.connectToDB();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, session.getSessionId());
			preparedStatement.setLong(2, session.getUserId());
			preparedStatement.setLong(3, session.getCreatedAt());
			preparedStatement.setLong(4, session.getLastAccessed());
			preparedStatement.setLong(5, session.getExpiresAt());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateSessionInDatabase(Session session) {
		String query = "UPDATE Session SET lastAccessed = ?, expiresAt = ? WHERE sessionId = ?";
		try (Connection connection = Executor.connectToDB();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setLong(1, session.getLastAccessed());
			preparedStatement.setLong(2, session.getExpiresAt());
			preparedStatement.setString(3, session.getSessionId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
