package com.querylayer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class DemoExecutor {
	private static String url = "jdbc:mysql://localhost:3306/Jojo";
	private static String user = "root";
	private static String password = "root";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	public static Connection connectToDB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public <T> List<T> umarPopulator(Class<T> clazz, Long uniqueId, String whatId) throws ClassNotFoundException,
			SQLException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		List<T> resultList = new ArrayList<>();
		String query = "SELECT * FROM " + clazz.getSimpleName();
		if (uniqueId != null) {
			query += " WHERE " + whatId + " = " + uniqueId;
		}
		query += ";";
		System.out.println(query);
		Connection conn = connectToDB();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();
		String tableName = metaData.getTableName(1).toLowerCase();
		int colCount = metaData.getColumnCount();
		while (rs.next()) {
			Long uId = rs.getLong(1);
			String wId = metaData.getColumnName(1);			
			T pojo = clazz.getDeclaredConstructor().newInstance();
			for (Field field: clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getType().equals(ArrayList.class)) {
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Type type = listType.getActualTypeArguments()[0];
					Class<T> typeClass = (Class<T>) Class.forName(type.getTypeName());
					field.set(pojo, umarPopulator(typeClass, uId, wId));
				}
				else {
					field.set(pojo, rs.getObject(field.getName()));
				}
			}
			resultList.add(pojo);
		}
		return resultList;
	}
}
