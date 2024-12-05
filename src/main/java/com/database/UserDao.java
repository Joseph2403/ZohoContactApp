package com.database;

import java.sql.*;
import com.pojo.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;

public class UserDao {
	private static String url = "		jdbc:mysql://localhost:3306/Jojo";
	private static String user = "root";
	private static String password = "root";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	public static Connection connectToDB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
		
	}

	public static User getUserPojo(long userId) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "SELECT u.*, GROUP_CONCAT(DISTINCT ue.userEmail) AS userEmails, GROUP_CONCAT(DISTINCT ue.isPrime) AS isPrime, GROUP_CONCAT(DISTINCT up.userPhone) AS userPhones FROM User u LEFT JOIN UserEmail ue ON u.userId = ue.userId LEFT JOIN UserPhone up ON u.userId = up.userId WHERE u.userId = ? GROUP BY u.userId, u.name;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			UserEmail userEmail;
			User user = new User(rs.getString("password"), rs.getString("name"), rs.getString("dateOfBirth"),
					rs.getInt("age"), rs.getString("state"), rs.getString("city"));
			ArrayList<UserEmail> userEmails = new ArrayList<>();
			ArrayList<Long> userPhones = new ArrayList<>();
			System.out.println("PrimaryCheck " + rs.getString("isPrime"));
			String[] dbEmails = rs.getString("userEmails").split(",");
			String[] dbIsPrimes = rs.getString("isPrime").split(",");
			String[] dbPhones = rs.getString("userPhones").split(",");

			for (int i = 0; i < dbEmails.length; i++) {
				String boolValue = dbIsPrimes[i].equals("1") ? "true" : "false";
				userEmail = new UserEmail(dbEmails[i], Boolean.parseBoolean(boolValue));
				userEmails.add(userEmail);
			}

			for (int j = 0; j < dbPhones.length; j++) {
				userPhones.add(Long.parseLong(dbPhones[j]));
			}
			user.setUserId(userId);
			user.setUserEmail(userEmails);
			user.setUserPhone(userPhones);
			return user;
		}
		return null;
	}

	public static long insertUser(User user) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "insert into User(password, name, dateOfBirth, age, state, city) values (?, ?, ?, ?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getPassword());
		ps.setString(2, user.getName());
		ps.setString(3, user.getDateOfBirth());
		ps.setInt(4, user.getAge());
		ps.setString(5, user.getState());
		ps.setString(6, user.getCity());
		long rs = ps.executeUpdate();
		if (rs > 0) {
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
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

	public static boolean insertUserEmail(long userId, String mailId, boolean isPrime)
			throws ClassNotFoundException, SQLException {
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

	public static int hashPassword(String password, int type, Connection conn, long userId) throws SQLException {
		String hashedPassword = null;
		int res = 0;
		PreparedStatement ps;
		String query;
		switch (type) {
		case 1:
			Argon2 argon2 = Argon2Factory.create();
			hashedPassword = argon2.hash(10, 65535, 1, password.toCharArray());
			query = "update User set password = ?, passCheck = 1 where userId = ?;";
			ps = conn.prepareStatement(query);
			ps.setString(1, hashedPassword);
			ps.setLong(2, userId);
			res = ps.executeUpdate();
			break;
		case 2:
			hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			query = "update User set password = ?, passCheck = 2 where userId = ?;";
			ps = conn.prepareStatement(query);
			ps.setString(1, hashedPassword);
			ps.setLong(2, userId);
			res = ps.executeUpdate();
			break;
		default:
			res = 0;
		}
		return res;
	}

	public static boolean checkPassword(String password, String hashedPassword, int type) {
		boolean legit = false;
		switch (type) {
		case 0:
			legit = password.equals(hashedPassword);
			break;
		case 1:
			Argon2 argon2 = Argon2Factory.create();
			legit = argon2.verify(hashedPassword, password.toCharArray());
			break;
		case 2:
			legit = BCrypt.checkpw(password, hashedPassword);
			break;
		default:
			legit = false;
		}
		return legit;
	}

	public static User loginUser(String mailId, String password) throws ClassNotFoundException, SQLException {
		int algoNum = 2;
		Connection conn = connectToDB();
		String query = "select * from User u join UserEmail ue on u.userId = ue.userId where userEmail = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mailId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int passCheck = rs.getInt("passCheck");
			
			boolean isValidPassword =  checkPassword(password, rs.getString("password"), passCheck);
			
			if (isValidPassword && passCheck != algoNum) {
				isValidPassword = hashPassword(password, algoNum, conn, rs.getLong("userId")) > 0;
			}
			
			if (isValidPassword) {
				User user = getUserPojo(rs.getLong("userId"));
				return user;
			}

			return null;
		}
		return null;
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

	public static boolean deleteUserPhone(long userId, long phoneNumber) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "delete from UserPhone where userphone = ? and userId = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, phoneNumber);
		ps.setLong(2, userId);
		int rs = ps.executeUpdate();
		return rs > 0;
	}

	public static int countUserPhone(long userId) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "select count(userPhone) count from UserPhone where userId = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt("count");
		}
		return 0;
	}

	public static boolean changePrimaryEmail(String mailId, long userId) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "update UserEmail set isPrime = case when userEmail = ? then 1 else 0 end where userId = ? ;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mailId);
		ps.setLong(2, userId);
		int rs = ps.executeUpdate();
		return rs > 0;
	}

	public static boolean editUserDetails(long userId, String name, int age, String dateOfBirth, String state,
			String city) throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		String query = "update User set name = ?, age = ?, dateOfBirth = ?, state = ?, city = ? where userId = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, name);
		ps.setInt(2, age);
		ps.setString(3, dateOfBirth);
		ps.setString(4, state);
		ps.setString(5, city);
		ps.setLong(6, userId);
		int rs = ps.executeUpdate();
		return rs > 0;
	}

	public static boolean hashPassword(String password) {
		return false;
	}
}