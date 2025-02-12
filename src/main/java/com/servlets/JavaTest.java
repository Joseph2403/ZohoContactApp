package com.servlets;

import java.lang.reflect.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.pojo.*;
import com.querylayer.DemoExecutor;
import com.querylayer.Executor;
import com.querylayer.Executor.SelectExecutor;
import com.querylayer.JojoDB;
import com.querylayer.QueryBuilder2;

public class JavaTest {
	ArrayList<String> demo;
	private static String url = "jdbc:mysql://localhost:3306/Jojo";
	private static String user = "root";
	private static String password = "root";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	public static Connection connectToDB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static void main(String args[])
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, NoSuchFieldException, SQLException {
		// ----------Actual Type----------
//		Class<?> clazz = User.class;
//		Object pojo = clazz.getDeclaredConstructor().newInstance();
//		for (Field field : clazz.getDeclaredFields()) {
//			if (field.getType().equals(ArrayList.class)) {
//				ParameterizedType listType = (ParameterizedType) field.getGenericType();
//				Type type = listType.getActualTypeArguments()[0];
//				Class<?> actualType = Class.forName(type.getTypeName());
//				System.out.println(actualType);
//			}
//		}

		// ----------Field Operations----------
//		Field field2 = (User.class).getDeclaredField("name");
//		System.out.println(field2);

		// ----------DB Operations----------
//		Connection conn = connectToDB();
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("select name, age from User where userId = 7;");
//		ResultSetMetaData metaData = rs.getMetaData();
//		
//		Class<?> clazz = User.class;
////		System.out.println(metaData.getColumnCount());
////		System.out.println(metaData.getColumnType(0)+" -> "+metaData.getColumnName(0));
//		
//		rs.next();
//		Object pojo = clazz.getDeclaredConstructor().newInstance();
//		for (int i=1; i<=metaData.getColumnCount(); i++) {
//			Field field = clazz.getDeclaredField(metaData.getColumnName(i));
//			field.set(pojo, rs.getObject(metaData.getColumnName(i)));
//		}
//		
//		User out = (User) pojo;
//		System.out.println(out.city);
//		System.out.println(metaData.getTableName(1));

		// ----------DB Operations 2----------
		Connection conn = connectToDB();
		QueryBuilder2 qb = new QueryBuilder2();
		qb.select().from(JojoDB.Table.USER)
				.join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.USEREMAIL, JojoDB.UserTab.USERID,
						JojoDB.UserEmailTab.USERID)
				.join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.USERPHONE, JojoDB.UserTab.USERID,
						JojoDB.UserPhoneTab.USERID)
				.build();
		QueryBuilder2 qb2 = new QueryBuilder2();
		qb2.select().from(JojoDB.Table.CONTACT)
				.join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.CONTACTEMAIL, JojoDB.ContactTab.CONTACTID,
						JojoDB.ContactEmailTab.CONTACTID)
				.where(JojoDB.ContactTab.USERID, JojoDB.Comparisons.EQUALS, "6").build();
		System.out.println(qb2.finalQuery);
//		System.out.println(qb.finalQuery);
//		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = stmt.executeQuery(qb.finalQuery);
//		ResultSetMetaData metaData = rs.getMetaData();
////		
//		DemoExecutor de = new DemoExecutor();
//		List<User> pojos = de.umarPopulator(User.class, null, "siudhfsd");
//		for (User pojo : pojos) {
//			ArrayList<UserEmail> emails = pojo.getUserEmail();
//			System.out.println(pojo.name + "\nEmail Count: " + pojo.getUserEmail().size() + "\nPhone Count: "
//					+ pojo.getUserPhone().size() + "\n");
//		}
//		System.out.println(pojos.size());

//		int row = rs.getRow();
//		while (rs.next()) {
//			rs.next();
//			System.out.println(row);
//		}

//		List<User> pojos = (ArrayList<User>) Executor.executeSelect(rs, qb.getMainClass()).demoExecute(rs,qb , qb.getMainClass());
//		for (User pojo: pojos) {
//			ArrayList<UserEmail> emails = pojo.getUserEmail();
//			System.out.println(pojo.getName()+"'s POJO:");
//			for (UserEmail email: emails) {
//				System.out.println(email.getUserEmail()+", ");
//			}
//			System.out.println();
//		}
//		System.out.println(pojos.size());

//		List<String> chumma = new List<>();
//		rs.next();
//		rs.previous();
//		rs.previous();
//		rs.previous();
//		rs.previous();
//		rs.next();
//		System.out.println(rs.getString("name"));

		// ---------- Set Operations---------
//		Set<String> demoSet = new LinkedHashSet<>();
//		String[] demoArray = {"apple", "apple", "mango", "kiwi", "mango"};
//		for (String fruit: demoArray) {
//			demoSet.add(fruit);
//		}
//		String[] array =  demoSet.toArray(new String[0]);
//		System.out.println(array[array.length-1]);
	}

}
