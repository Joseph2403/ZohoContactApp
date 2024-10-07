package com.database;

import java.sql.*;

public class UserDao { 
    private static String url = "jdbc:mysql://localhost:3306/Jojo";
    private static String user = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static long insertUser(String password, String name, String dateOfBirth, int age, String state, String city) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into User(password, name, dateOfBirth, age, state, city) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, password);
        ps.setString(2, name);
        ps.setString(3, dateOfBirth);
        ps.setInt(4, age);
        ps.setString(5, state);
        ps.setString(6, city);
        long rs = ps.executeUpdate();
        if(rs > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        }
        return 0;
    }

    public static boolean insertUserPhone(long userId, long phoneNumber) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into UserPhone values (?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ps.setLong(2, phoneNumber);
        int rs = ps.executeUpdate();
        return rs > 0;
    }

    public static boolean insertUserEmail(long userId, String mailId, boolean isPrime) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into UserEmail values (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ps.setString(2, mailId);
        ps.setBoolean(3, isPrime);
        int rs = ps.executeUpdate();
        return rs > 0;
    }

    public static long getUserId(String mailId) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "select userId from UserEmail where userEmail = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, mailId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getLong("userId");
        }
        return -1;
    }

    public static ResultSet loginUser(String mailId, String password) throws ClassNotFoundException, SQLException {
        long userId = getUserId(mailId);
        Connection conn = connectToDB();
        String query = "select * from User where userId = ? and password  = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getUserDetails(long userId) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "select * from User where userId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public static ResultSet getUserEmailDetails(long userId) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "select * from UserEmail where userId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public static ResultSet getUserPhoneDetails(long userId) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "select * from UserPhone where userId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public static boolean checkExistingEmail(String mailId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select * from UserEmail where userEmail = ?";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, mailId);
        ResultSet rs = ps.executeQuery();
    	return rs.next();
    }
    
    public static boolean deleteUserEmail(String mailId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "delete from UserEmail where userEmail = ? and isPrime = 0;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, mailId);
        int rs = ps.executeUpdate();
    	return rs > 0;
    }
    
    public static boolean changePrimaryEmail(String mailId, long userId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query ="update UserEmail set isPrime = case when userEmail = ? then 1 else 0 end where userId = ? ;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, mailId);
        ps.setLong(2, userId);
        int rs = ps.executeUpdate();
    	return rs > 0;
    }
}