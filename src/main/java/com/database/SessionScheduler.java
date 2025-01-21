package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.querylayer.Executor;

public class SessionScheduler {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            String deleteExpiredSessionsQuery = "DELETE FROM Session WHERE expiresAt < ?;";

            try (Connection connection = Executor.connectToDB();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteExpiredSessionsQuery)) {

                long currentTimeMillis = System.currentTimeMillis();
                preparedStatement.setLong(1, currentTimeMillis);

                int rowsDeleted = preparedStatement.executeUpdate();
                System.out.println("Expired sessions cleaned up: " + rowsDeleted);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.DAYS.SECONDS);
    }
}
