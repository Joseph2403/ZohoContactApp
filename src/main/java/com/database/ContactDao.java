package com.database;

import java.sql.*;
import java.util.ArrayList;
import com.pojo.*;

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
    
    public static ArrayList<Contact> getUserContactPojo (long userId) throws ClassNotFoundException, SQLException {
    	ArrayList<Contact> contacts = new ArrayList<>();
    	Connection conn = connectToDB();
        String query = "select c.*, group_concat(distinct ce.contactEmail) as contactEmails, group_concat(distinct cp.contactPhone) as contactPhones from Contact c left join ContactEmail ce on c.contactId = ce.contactId left join ContactPhone cp on c.contactId = cp.contactId where c.userId = ? group by c.contactId;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	ContactEmail contactEmail;
        	ContactPhone contactPhone;
        	do {
        	Contact contact = new Contact(
        			rs.getString("name"),
        			rs.getInt("age"),
        			rs.getString("dateOfBirth"),
        			rs.getString("state"),
        			rs.getString("city")
        			);
        	ArrayList<ContactEmail> contactEmails = new ArrayList<>();
        	ArrayList<ContactPhone> contactPhones = new ArrayList<>();
        	String[] dbEmails = ((String) rs.getString("contactEmails")).split(",");
        	String[] dbPhones = ((String) rs.getString("contactPhones")).split(",");
        	
        	for (int i=0; i < dbEmails.length; i++) {
        		contactEmail =  new ContactEmail(rs.getLong("contactId"), rs.getString("contactEmail"));
        		contactEmails.add(contactEmail);
        	}
        	
        	
        	for (int j=0; j < dbPhones.length; j++) {
        		contactPhone = new ContactPhone(rs.getLong("contactId"), rs.getLong("contactPhone"));
        		contactPhones.add(contactPhone);
        	}
        	contact.setContactId(rs.getLong("contactId"));
        	contact.setContactEmail(contactEmails);
        	contact.setContactPhone(contactPhones);
        	contacts.add(contact);
        	} while (rs.next());
        	return contacts;
        }
		return null;	
    }
    
    public static Contact getContactPojo(long contactId) throws SQLException, ClassNotFoundException {
    	Connection conn = connectToDB();
    	String query = "select c.*, group_concat(distinct ce.contactEmail) as contactEmails, group_concat(distinct cp.contactPhone) as contactPhones from Contact c left join ContactEmail ce on c.contactId = ce.contactId left join ContactPhone cp on c.contactId = cp.contactId where c.contactId = ? group by c.contactId;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, contactId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	ContactEmail contactEmail;
        	ContactPhone contactPhone;
        	Contact contact = new Contact(
        			rs.getString("name"),
        			rs.getInt("age"),
        			rs.getString("dateOfBirth"),
        			rs.getString("state"),
        			rs.getString("city")
        			);
        	ArrayList<ContactEmail> contactEmails = new ArrayList<>();
        	ArrayList<ContactPhone> contactPhones = new ArrayList<>();
        	String[] dbEmails = ((String) rs.getString("contactEmails")).split(",");
        	String[] dbPhones = ((String) rs.getString("contactPhones")).split(",");
        	
        	for (int i=0; i < dbEmails.length; i++) {
        		contactEmail =  new ContactEmail(rs.getLong("contactId"), rs.getString("contactEmail"));
        		contactEmails.add(contactEmail);
        	}
        	
        	
        	for (int j=0; j < dbPhones.length; j++) {
        		contactPhone = new ContactPhone(rs.getLong("contactId"), rs.getLong("contactPhone"));
        		contactPhones.add(contactPhone);
        	}
        	
        	contact.setContactId(rs.getLong("contactId"));
        	contact.setContactEmail(contactEmails);
        	contact.setContactPhone(contactPhones);
        	return contact;
        }
		return null;	
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
    
    public static boolean insertContactPhone(long userId, String[] phoneNumbers) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into ContactPhone values (?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        for (String s: phoneNumbers) {
        	long phoneNumber = Long.parseLong(s);
        	ps.setLong(1, userId);
            ps.setLong(2, phoneNumber);
            ps.addBatch();
        }
        
        int[] rs = ps.executeBatch();
    	for(int n: rs) {
    		if (n < 1) return false;
    	}
    	return true;
    }
    
    public static boolean insertContactEmail(long userId, String[] mailIds) throws ClassNotFoundException, SQLException {
        Connection conn = connectToDB();
        String query = "insert into ContactEmail values (?, ?);";
        PreparedStatement ps = conn.prepareStatement(query);
        for (String mailId: mailIds) {
        ps.setLong(1, userId);
        ps.setString(2, mailId);
        ps.addBatch();
        }
        int[] rs = ps.executeBatch();
    	for(int n: rs) {
    		if (n < 1) return false;
    	}
    	return true;
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
    
    public static boolean deleteContact(long contactId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "delete from Contact where contactId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, contactId);
        int rs = ps.executeUpdate();
    	return rs > 0;
    }
}