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
		try (Connection conn = connectToDB();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			ResultSet rs = stmt.executeQuery(this.qb.finalQuery);
			ResultSetMetaData metaData = rs.getMetaData();
			return new JojoResult(rs, qb, qb.getMainClass());
		}
	}

	public long executeUpdate() throws ClassNotFoundException, SQLException {
		Connection conn = connectToDB();
		Statement stmt = conn.createStatement();
		int rs = stmt.executeUpdate(this.qb.finalQuery);
		return rs;
	}

	public long executeUpdate(Boolean value) throws ClassNotFoundException, SQLException {
		if (value) {
			long key = -1;
			try (Connection conn = connectToDB();
					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
				long rs = stmt.executeUpdate(this.qb.finalQuery, Statement.RETURN_GENERATED_KEYS);

				if (rs > 0) {
					try (ResultSet keys = stmt.getGeneratedKeys()) {
						if (keys.next()) {
							key = keys.getLong(1);
						}
					}
				}
			}

			return key;
		}

		return 0;
	}

}
