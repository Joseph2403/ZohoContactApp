package com.querylayer;

import java.util.*;

import com.pojo.Category;
import com.pojo.Contact;
import com.pojo.User;

public class QueryBuilder2 {
	ArrayList<String> tables = new ArrayList<>();
	public String finalQuery;
	String mainTable;
	String Command;

	public Class<?> getMainClass() {
		if (mainTable.equals("User")) {
			return User.class;
		}
		else if (mainTable.equals("Contact")) {
			return Contact.class;
		}
		else if (mainTable.equals("Category")) {
			return Category.class;
		}
		return null;
	}

	public InsertBuilder insert(JojoDB.Table table) {
		this.Command = "insert";
		return new InsertBuilder(table);
	}

	public DeleteBuilder delete(JojoDB.Table table) {
		this.Command = "delete";
		return new DeleteBuilder(table);
	}

	public UpdateBuilder update(JojoDB.Table table) {
		this.Command = "update";
		return new UpdateBuilder(table);
	}

	public SelectBuilder select(EnumColumn... columns) {
		this.Command = "select";
		return new SelectBuilder(columns);
	}

	public class InsertBuilder {
		private StringBuilder query = new StringBuilder();

		public InsertBuilder(JojoDB.Table table) {
			query.append("INSERT INTO ").append(table.getTableName());
		}

		public InsertBuilder columns(EnumColumn... columns) {
			ArrayList<String> colNew = new ArrayList<>();
			for (EnumColumn col : columns) {
				colNew.add(col.getColumnName());
			}
			query.append(" (").append(String.join(", ", colNew)).append(")");
			return this;
		}

		public InsertBuilder values(String... values) {
			query.append(" VALUES (").append(String.join(", ", values)).append(")");
			return this;
		}

		public String build() {
			return query.append(";").toString();
		}
	}

	public class DeleteBuilder {
		private StringBuilder query = new StringBuilder();

		public DeleteBuilder(JojoDB.Table table) {
			query.append("DELETE FROM ").append(table);
		}

		public DeleteBuilder where(EnumColumn column, JojoDB.Comparisons operator, String value) {
			this.query.append(" WHERE ");
			this.query.append(column.getColumnName() + " " + operator.getOperator() + " " + value);
			return this;
		}

		public String build() {
			return query.append(";").toString();
		}
	}

	public class UpdateBuilder {
		private StringBuilder query = new StringBuilder();
		private ArrayList<String> columns = new ArrayList<>();
		private ArrayList<String> values = new ArrayList<>();

		public UpdateBuilder(JojoDB.Table table) {
			query.append("UPDATE ").append(table.getTableName());
		}

		public UpdateBuilder columns(EnumColumn... columns) {
			for (EnumColumn column : columns) {
				this.columns.add(column.getColumnName());
			}
			return this;
		}

		public UpdateBuilder values(String... values) {
			for (String value : values) {
				this.values.add(value);
			}
			return this;
		}

		public UpdateBuilder set() {
			query.append(" SET ");
			for (int i = 0; i < this.columns.size(); i++) {
				String column = this.columns.get(i);
				String value = this.values.get(i);
				query.append(column + " = " + value);
				if (i < this.columns.size() - 1) {
					query.append(", ");
				}
			}
			return this;
		}

		public UpdateBuilder where(EnumColumn column, JojoDB.Comparisons operator, String value) {
			this.query.append(" WHERE ");
			this.query.append(column.getColumnName() + " " + operator.getOperator() + " " + value);
			return this;
		}

		public String build() {
			return query.append(";").toString();
		}

	}

	public class SelectBuilder {
		private StringBuilder query = new StringBuilder();
		private ArrayList<EnumColumn> columns = new ArrayList<>();
		private String table;
		private ArrayList<Condition> conditions = new ArrayList<>();
		private ArrayList<Join> joins = new ArrayList<>();
		private ArrayList<EnumColumn> groups = new ArrayList<>();

		public SelectBuilder(EnumColumn... columns) {
			for (EnumColumn column : columns) {
				this.columns.add(column);
			}
		}

		public SelectBuilder from(JojoDB.Table table) {
			this.table = table.getTableName();
			mainTable = table.getTableName();
			return this;
		}

		public SelectBuilder where(EnumColumn column, JojoDB.Comparisons operator, String value) {
			this.conditions.add(new Condition(column.getColumnName(), operator.getOperator(), value));
			return this;
		}

		public SelectBuilder join(JojoDB.JoinType joinType, JojoDB.Table table, EnumColumn column1,
				EnumColumn column2) {
			this.joins.add(new Join(joinType.getJoin(), table.getTableName(), column1.getColumnName(),
					column2.getColumnName()));
			return this;
		}

		public SelectBuilder groupBy(EnumColumn... columns) {
			for (EnumColumn column : columns) {
				this.groups.add(column);
			}
			return this;
		}

		// public SelectBuilder joinAll() {
		// for (Join join: this.joins) {
		// join.column = !join.table.tableAlias.isEmpty() ?
		// join.table2.tableAlias+"."+join.column :
		// join.table.tableName+"."+join.column;
		// join.column2 = !join.table2.tableAlias.isEmpty() ?
		// join.table2.tableAlias+"."+join.column2 :
		// join.table2.tableName+"."+join.column2;
		// query.append(" "+join.joinType+" ").append(join.table.tableName+"
		// "+join.table.tableAlias).append(" ON ").append(join.column).append(" =
		// ").append(join.column2);
		// }
		// return this;
		// }

		public String build() {
			this.query.append("SELECT ");
			int i = 1;
			if (this.columns.size() == 0) {
				this.query.append("*");
			}
			else {
				for (EnumColumn column : this.columns) {
					this.query.append(column.getColumnName());
					if (i < this.columns.size()) {
						this.query.append(", ");
					}
					i++;
				}
			}
			this.query.append(" FROM " + this.table);
			tables.add(this.table);
			// if (!this.table.tableAlias.isEmpty()) {
			// this.query.append(" "+ this.table.tableAlias);
			// }

			// if (!this.joins.isEmpty()) {
			// joinAll();
			// }

			if (!this.joins.isEmpty()) {
				for (Join join : this.joins) {
					this.query.append(
							" " + join.joinType + " " + join.table + " ON " + join.column1 + " = " + join.column2);
					tables.add(join.table);
				}
			}

			if (!this.conditions.isEmpty()) {
				int j = 1;
				this.query.append(" WHERE ");
				for (Condition cond : this.conditions) {
					this.query.append(cond.column + " " + cond.operator + " " + cond.value);
					if (j < this.conditions.size()) {
						this.query.append(" AND ");
					}
					j++;
				}
			}

			if (!this.groups.isEmpty()) {
				int j = 1;
				this.query.append(" GROUP BY ");
				for (EnumColumn column : this.groups) {
					this.query.append(column.getColumnName());
					if (j < this.groups.size()) {
						this.query.append(", ");
					}
					j++;
				}
			}
			this.query.append(";");
			finalQuery = this.query.toString();
			return this.query.toString();
		}

	}

	public static void demo(EnumColumn... columns) {
		for (EnumColumn col : columns) {
			System.out.println(col.getColumnName());
		}
	}

	public static void main(String[] args) {

		/*
		 * String selectQuery = QueryBuilder.select("NameOfEmployee name", "age",
		 * "IsHeRich status") .aggregate("age SUM", "NameOfEmployee AVG")
		 * .from("User u") .join("left join", "UserEmail", "ue", "User", "u", "userId",
		 * "userId") .join("join", "UserPhone", "up", "UserEmail", "ue", "userId",
		 * "userId") .where("name = 'Christopher'", "age > 18") .build();
		 */

		// String selectQuery2 = QueryBuilder.select(JojoDB.UserTab.NAME,
		// JojoDB.UserTab.AGE).from(JojoDB.Table.USER).join(JojoDB.JoinType.INNER_JOIN,
		// JojoDB.Table.CATEGORY, JojoDB.CategoryTab.USERID,
		// JojoDB.UserTab.USERID).where(JojoDB.UserTab.NAME, JojoDB.Comparisons.EQUALS,
		// "Christopher").where(JojoDB.UserTab.AGE,
		// JojoDB.Comparisons.GREATER_THAN_OR_EQUALS, "20").build();
		// System.out.println("SELECT Query 2: "+selectQuery2);*/
//        String selectQuery = QueryBuilder.select(JojoDB.UserTab.ALL, JojoDB.UserEmailTab.USEREMAIL.alias("userEmails").aggregate(JojoDB.Aggregation.GROUP_CONCAT))
//                .from(JojoDB.Table.USER)
//                .join(JojoDB.JoinType.LEFT_JOIN, JojoDB.Table.USEREMAIL, JojoDB.UserTab.USERID, JojoDB.UserEmailTab.USERID)
//                .groupBy(JojoDB.UserTab.USERID, JojoDB.UserTab.NAME).build();
		QueryBuilder2 qb = new QueryBuilder2();
		qb.select(JojoDB.UserTab.ALL, JojoDB.UserEmailTab.USEREMAIL, JojoDB.UserPhoneTab.USERPHONE)
				.from(JojoDB.Table.USER)
				.join(JojoDB.JoinType.LEFT_JOIN, JojoDB.Table.USEREMAIL, JojoDB.UserTab.USERID,
						JojoDB.UserEmailTab.USERID)
				.join(JojoDB.JoinType.LEFT_JOIN, JojoDB.Table.USERPHONE, JojoDB.UserTab.USERID,
						JojoDB.UserPhoneTab.USERID)
				.where(JojoDB.UserTab.USERID, JojoDB.Comparisons.EQUALS, "7").build();
		
		
		QueryBuilder2 qb2 = new QueryBuilder2();
		qb2.select(JojoDB.UserTab.ALL, JojoDB.UserEmailTab.ALL).from(JojoDB.Table.USER).join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.USEREMAIL,
				JojoDB.UserTab.USERID, JojoDB.UserEmailTab.USERID).build();
		
		System.out.println(qb2.finalQuery);
//    	String demo = QueryBuilder.select(JojoDB.UserEmailTab.ALL, JojoDB.UserTab.ALL).from(JojoDB.Table.USEREMAIL).where(JojoDB.UserEmailTab.USEREMAIL, JojoDB.Comparisons.EQUALS, "taufeeq@gmail.com").join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.USER, JojoDB.UserTab.USERID, JojoDB.UserEmailTab.USERID).build();
//        String insertQuery = QueryBuilder.insert(JojoDB.Table.USER)
//                .columns(JojoDB.UserTab.NAME, JojoDB.UserTab.AGE, JojoDB.UserTab.DATEOFBIRTH)
//                .values("Christopher", "20", "24.03.2004")
//                .build();
//        String deleteQuery = QueryBuilder.delete(JojoDB.Table.USER)
//                .where(JojoDB.UserTab.USERID, JojoDB.Comparisons.EQUALS, "8")
//                .build();
//        String updateQuery = QueryBuilder.update(JojoDB.Table.USER)
//                .columns(JojoDB.UserTab.NAME, JojoDB.UserTab.AGE, JojoDB.UserTab.DATEOFBIRTH)
//                .values("Joseph", "20", "24.03.2004").set()
//                .where(JojoDB.UserTab.USERID, JojoDB.Comparisons.EQUALS, "8")
//                .build();
		/*
		 * System.out.println("SELECT Query: "+selectQuery);
		 * System.out.println("INSERT Query: "+insertQuery);
		 * System.out.println("DELETE Query: "+deleteQuery);
		 * System.out.println("UPDATE Query: "+updateQuery);
		 */

		// System.out.println(JojoDB.UserTab.PASSWORD.alias("pwd").aggregate(JojoDB.Aggregation.GROUP_CONCAT).getColumnName());
		// System.out.println(JojoDB.Table.CONTACTPHONE);

		/*
		 * SELECT u.*, GROUP_CONCAT(DISTINCT ue.userEmail) AS userEmails,
		 * GROUP_CONCAT(DISTINCT ue.isPrime) AS isPrime, GROUP_CONCAT(DISTINCT
		 * up.userPhone) AS userPhones FROM User u LEFT JOIN UserEmail ue ON u.userId =
		 * ue.userId LEFT JOIN UserPhone up ON u.userId = up.userId WHERE u.userId = ?
		 * GROUP BY u.userId, u.name;
		 */
//		System.out.println("\nSELECT QUERY: " + qb.finalQuery);
//		for (String table : qb.tables) {
//			System.out.print(table + ", ");
//		}
//        System.out.println("\nINSERT QUERY: "+insertQuery);
//        System.out.println("\nUPDATE QUERY: "+updateQuery);
//        System.out.println("\nDELETE QUERY: "+deleteQuery+"\n");
	}
}