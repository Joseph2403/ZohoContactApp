package com.querylayer;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JojoStmt {
	QueryBuilder2 qb;
	private static String url = "jdbc:mysql://localhost:3306/Jojo";
	private static String user = "root";
	private static String password = "root";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	public static Connection connectToDB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public JojoStmt(QueryBuilder2 queryBuilder) {
		this.qb = queryBuilder;
	}

	public <T> JojoResult executeQuery()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, NoSuchFieldException {
		Connection conn = connectToDB();
		Statement stmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
			    ResultSet.CONCUR_READ_ONLY
			    );
		ResultSet rs = stmt.executeQuery(this.qb.finalQuery);
//		HashMap<String, Object> pojos = new HashMap<>();
		ResultSetMetaData metaData = rs.getMetaData();
		return new JojoResult(rs, qb, qb.getMainClass());
	}

	public long executeUpdate() {
		return 0;
	}

	public long executeUpdate(Boolean value) {
		if (value) {

		}
		return 0;
	}

}
