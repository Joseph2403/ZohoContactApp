package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.querylayer.Executor;

/**
 * Application Lifecycle Listener implementation class SessionScheduler2
 *
 */
@WebListener
public class SessionScheduler2 implements ServletContextListener {
	ScheduledExecutorService scheduler;

	public SessionScheduler2() {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (scheduler != null) {
			scheduler.shutdown();
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Session Scheduler Initialized");
		scheduler = Executors.newScheduledThreadPool(1);

		scheduler.scheduleAtFixedRate(() -> {
			String deleteExpiredSessionsQuery = "DELETE FROM Session WHERE expiresAt < ?;";

			try (Connection connection = Executor.connectToDB();
					PreparedStatement preparedStatement = connection.prepareStatement(deleteExpiredSessionsQuery)) {

				long currentTimeMillis = System.currentTimeMillis();
				preparedStatement.setLong(1, currentTimeMillis);

				int rowsDeleted = preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}, 0, 2, TimeUnit.DAYS.SECONDS);
	}

}
