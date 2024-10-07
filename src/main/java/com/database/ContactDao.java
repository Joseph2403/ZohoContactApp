package com.database;

import java.sql.*;

public class ContactDao {
    private static String url = "jdbc:mysql://localhost:3306/Jojo";
    private static String user = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
    
    public static long insertContact(long userId, String name, int age, String dateOfBirth, String state, String city) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into Contact(userId, name, age, dateOfBirth, state, city) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, userId);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.setString(4, dateOfBirth);
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
    
    public static boolean insertContactPhone(long userId, long phoneNumber) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into ContactPhone values (?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ps.setLong(2, phoneNumber);
        int rs = ps.executeUpdate();
        return rs > 0;
    }
    
    public static boolean insertContactEmail(long userId, String mailId) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into ContactEmail values (?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ps.setString(2, mailId);
        int rs = ps.executeUpdate();
        return rs > 0;
    }
    
    public static ResultSet getContacts(long userId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select * from Contact where userId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
    	return rs;
    }
    
    public static ResultSet getContactDetails(long contactId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select * from Contact where contactId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, contactId);
        ResultSet rs = ps.executeQuery();
    	return rs;
    }
    
    public static ResultSet getContactEmail(long contactId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select * from ContactEmail where contactId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, contactId);
        ResultSet rs = ps.executeQuery();
    	return rs;
    }
    
    public static ResultSet getContactPhone(long contactId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select * from ContactPhone where contactId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, contactId);
        ResultSet rs = ps.executeQuery();
    	return rs;
    }
}