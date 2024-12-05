package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pojo.Category;
import com.pojo.Contact;

public class CategoryDao {
	private static String url = "jdbc:mysql://localhost:3306/Jojo";
    private static String user = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
    
    public static Category getCategoryDetails(long categoryId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select c.*, group_concat(contactId) contactIds from Category c join CategoryContact cc on c.categoryId = cc.categoryId where c.categoryId = ? group by c.categoryId;";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setLong(1, categoryId);
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
    		Category category = new Category(
    				rs.getString("categoryName")
    				);
    		ArrayList<Contact> contacts = new ArrayList<>();
    		String[] dbContactIds = ((String) rs.getString("contactIds")).split(",");
    		
    		for (int i=0; i < dbContactIds.length; i++) {
    			contacts.add(ContactDao.getContactPojo(Long.parseLong(dbContactIds[i])));
    		}
    		
    		category.setCategoryContacts(contacts);
    		category.setCategoryId(rs.getLong("categoryId"));
    		return category;
    	}
    	return null;
    }
    
    public static ArrayList<Category> getCategories(long userId) throws SQLException, ClassNotFoundException {
    	
    	ArrayList<Category> categories = new ArrayList<>();
    	Connection conn = connectToDB();
    	String query = "select c.*, group_concat(contactId) contactIds from Category c left join CategoryContact cc on c.categoryId = cc.categoryId where c.userId = ? group by c.categoryId;";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setLong(1, userId);
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
    		do {
    		String[] dbContactIds = new String[0];
    		Category category = new Category(
    				rs.getString("categoryName")
    				);
    		ArrayList<Contact> contacts = new ArrayList<>();
    		if (rs.getString("contactIds") != null) {
    		dbContactIds = rs.getString("contactIds").split(",");
    		} 
    		
    		for (int i=0; i < dbContactIds.length; i++) {
    			contacts.add(ContactDao.getContactPojo(Long.parseLong(dbContactIds[i])));
    		}
    		
    		category.setCategoryContacts(contacts);
    		category.setCategoryId(rs.getLong("categoryId"));
    		categories.add(category);
    		} while (rs.next());
    		return categories;
    	}
    	return null;
    }
    
    public static ArrayList<Contact> getCategoryContacts(long categoryId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "select c.contactId from Contact c where c.userId = (select userId from Category where categoryId = ?) and c.contactId not in (select cc.contactId from CategoryContact cc where cc.categoryId = ?);";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setLong(1, categoryId);
    	ps.setLong(2, categoryId);
    	ResultSet rs = ps.executeQuery();
    	ArrayList<Contact> contacts = new ArrayList<>();
    	if (rs.next()) {
    		do {
    			Contact contact = ContactDao.getContactPojo(rs.getLong("contactId"));
    			contacts.add(contact);
    		} while (rs.next());
    		return contacts;
    	}
    	return null;
    }
    
    public static ArrayList<Contact> getAddedCategoryContacts(long categoryId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
//    	String query = "select c.name, cp.contactPhone, c.contactId from (select * from CategoryContact where categoryId = ?) mine inner join Contact c on mine.contactId = c.contactId inner join ContactPhone cp on c.contactId = cp.contactId;";
    	String query = "select contactId from CategoryContact where categoryId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setLong(1, categoryId);
    	ResultSet rs = ps.executeQuery();
    	ArrayList<Contact> contacts = new ArrayList<>();
    	if (rs.next()) {
    		do {
    			Contact contact = ContactDao.getContactPojo(rs.getLong("contactId"));
    			contacts.add(contact);
    		} while (rs.next());
    		return contacts;
    	}
    	return null;
    }
    
    public static boolean addContactsToCategory(long categoryId, String[] contactIds) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "insert into CategoryContact values (?, ?);";
    	PreparedStatement ps = conn.prepareStatement(query);
    	for (String strcontactId: contactIds) {
			long contactId = Long.parseLong(strcontactId);
	    	ps.setLong(1, contactId);
	    	ps.setLong(2, categoryId);
	    	ps.addBatch();
		}
    	int[] rs = ps.executeBatch();
    	for(int n: rs) {
    		if (n < 1) return false;
    	}
    	return true;
    }
    
    public static boolean deleteContactFromCategory(long categoryId, long contactId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query = "delete from CategoryContact where contactId = ? and categoryId = ?;";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setLong(1, contactId);
    	ps.setLong(2, categoryId);
    	int rs = ps.executeUpdate();
    	return rs > 0;
    }
    
    public static boolean deleteCategory(long categoryId) throws ClassNotFoundException, SQLException {
    	Connection conn = connectToDB();
    	String query3 = "select * from CategoryContact where categoryId = ?";
    	String query = "delete from CategoryContact where categoryId = ?;";
    	String query2 = "delete from Category where categoryId = ?;";
    	PreparedStatement ps3 = conn.prepareStatement(query3);
    	ps3.setLong(1, categoryId);
    	PreparedStatement ps1 = conn.prepareStatement(query);
    	ps1.setLong(1, categoryId);
    	PreparedStatement ps2 = conn.prepareStatement(query2);
    	ps2.setLong(1, categoryId);
    	ResultSet rs3 = ps3.executeQuery();
    	if (rs3.next()) {
    		int rs1 = ps1.executeUpdate();
        	int rs2 = ps2.executeUpdate();
        	return (rs1 > 0) && (rs2 > 0);
    	}
    	int rs2 = ps2.executeUpdate();
    	return  (rs2 > 0);
    }
}

