package com.querylayer;

import com.pojo.Category;

public class JojoDB {

	public static enum Table {
		CATEGORY("Category", "cat"), CATEGORYCONTACT("CategoryContact", "catc"), CONTACT("Contact", "c"),
		CONTACTEMAIL("ContactEmail", "ce"), CONTACTPHONE("ContactPhone", "cp"), USER("User", "u"),
		USEREMAIL("UserEmail", "ue"), USERPHONE("UserPhone", "up");

		private String tableName;
		private String tableAlias;

		private Table(String tableName, String tableAlias) {
			this.tableName = tableName;
			this.tableAlias = tableAlias;
		}

		public Table alias(String alias) {
			this.tableAlias = alias;
			return this;
		}

		public String getTableName() {
//			return tableAlias == null ? tableName : tableName + " AS " + tableAlias;
			return tableName;
		}

		public String getTableAlias() {
			return tableAlias;
		}

		@Override
		public String toString() {
			return this.tableName;
		}
	}

	public enum Aggregation {
		SUM("SUM"), AVG("AVG"), COUNT("COUNT"), MAX("MAX"), MIN("MIN"), GROUP_CONCAT("GROUP_CONCAT"),
		DISTINCT("DISTINCT");

		private String aggregation;

		Aggregation(String aggregation) {
			this.aggregation = aggregation;
		}

		public String getAggregation() {
			return aggregation;
		}

		@Override
		public String toString() {
			return this.aggregation;
		}
	}

	public enum Comparisons {
		EQUALS("="), NOT_EQUALS("!="), GREATER_THAN(">"), LESS_THAN("<"), GREATER_THAN_OR_EQUALS(">="),
		LESS_THAN_OR_EQUALS("<="), LIKE("LIKE"), NOT_LIKE("NOT LIKE"), IN("IN"), NOT_IN("NOT IN"), IS_NULL("IS NULL"),
		IS_NOT_NULL("IS NOT NULL"), BETWEEN("BETWEEN"), NOT_BETWEEN("NOT BETWEEN");

		private String operator;

		Comparisons(String operator) {
			this.operator = operator;
		}

		public String getOperator() {
			return operator;
		}

		@Override
		public String toString() {
			return this.operator;
		}
	}

	public enum JoinType {
		INNER_JOIN("INNER JOIN"), LEFT_JOIN("LEFT JOIN"), RIGHT_JOIN("RIGHT JOIN"), FULL_JOIN("FULL JOIN"),
		CROSS_JOIN("CROSS JOIN"), SELF_JOIN("SELF JOIN"), NATURAL_JOIN("NATURAL JOIN");

		private String join;

		JoinType(String join) {
			this.join = join;
		}

		public String getJoin() {
			return join;
		}
	}

	public enum CategoryTab implements EnumColumn {
		ALL("*", null, null), CATEGORYID("categoryId", null, null), USERID("userId", null, null),
		CATEGORYNAME("categoryName", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		CategoryTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public CategoryTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public CategoryTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableAlias() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableAlias() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableAlias() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableAlias() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableAlias() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableAlias() {
			return JojoDB.Table.CATEGORY.getTableAlias();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}

		@Override
		public String getTableName() {
			return JojoDB.Table.CATEGORY.getTableName();
		}
	}

	public enum CategoryContactTab implements EnumColumn {
		ALL("*", null, null), CONTACTID("contactId", null, null), CATEGORYID("categoryId", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		CategoryContactTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public CategoryContactTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public CategoryContactTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableAlias() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableAlias() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableAlias() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableAlias() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableAlias() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableAlias() {
			return JojoDB.Table.CATEGORYCONTACT.getTableAlias();
		}

		public String getTableName() {
			return JojoDB.Table.CATEGORYCONTACT.getTableName();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum ContactTab implements EnumColumn {
		ALL("*", null, null), CONTACTID("contactId", null, null), USERID("userId", null, null),
		NAME("name", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		ContactTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public ContactTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public ContactTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableAlias() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableAlias() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableAlias() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableAlias() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableAlias() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableAlias() {
			return JojoDB.Table.CONTACT.getTableAlias();
		}

		public String getTableName() {
			return JojoDB.Table.CONTACT.getTableName();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum ContactEmailTab implements EnumColumn {
		ALL("*", null, null), EMAILID("emailId", null, null), CONTACTID("contactId", null, null),
		EMAIL("email", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		ContactEmailTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public ContactEmailTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public ContactEmailTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableAlias() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableAlias() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableAlias() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableAlias() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableAlias() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableAlias() {
			return JojoDB.Table.CONTACTEMAIL.getTableAlias();
		}

		public String getTableName() {
			return JojoDB.Table.CONTACTEMAIL.getTableName();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum ContactPhoneTab implements EnumColumn {
		ALL("*", null, null), PHONEID("phoneId", null, null), CONTACTID("contactId", null, null),
		PHONE("phone", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		ContactPhoneTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public ContactPhoneTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public ContactPhoneTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableAlias() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableAlias() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableAlias() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableAlias() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableAlias() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableAlias() {
			return JojoDB.Table.CONTACTPHONE.getTableAlias();
		}

		public String getTableName() {
			return JojoDB.Table.CONTACTPHONE.getTableName();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum UserTab implements EnumColumn {
		ALL("*", null, null), USERID("userId", null, null), PASSWORD("password", null, null), NAME("name", null, null),
		DATEOFBIRTH("dateOfBirth", null, null), AGE("age", null, null), STATE("state", null, null),
		CITY("city", null, null), PASSCHECK("passCheck", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		UserTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public UserTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public UserTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableName() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableName() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableName() + "." + columnName + " AS \"" + columnAlias + "\"";
			} else {
				return columnAggregation.toString() == "GROUP_CONCAT"
						? columnAggregation + "(DISTINCT " + getTableName() + "." + columnName + ")" + " AS "
								+ columnAlias
						: columnAggregation + "(" + getTableName() + "." + columnName + ")" + " AS " + columnAlias;
			}

		}

		public String getTableName() {
			return JojoDB.Table.USER.getTableName();
		}

		public String getTableAlias() {
			return JojoDB.Table.USER.getTableAlias();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum UserEmailTab implements EnumColumn {
		ALL("*", null, null), USEREMAIL("userEmail", null, null), USERID("userId", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		UserEmailTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public UserEmailTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public UserEmailTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableName() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableName() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableName() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableName() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableName() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableName() {
			return JojoDB.Table.USEREMAIL.getTableName();
		}

		public String getTableAlias() {
			return JojoDB.Table.USEREMAIL.getTableAlias();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

	public enum UserPhoneTab implements EnumColumn {
		ALL("*", null, null), USERID("userId", null, null), USERPHONE("userPhone", null, null);

		private String columnName;
		private String columnAlias;
		private Aggregation columnAggregation;

		UserPhoneTab(String columnName, String columnAlias, Aggregation columnAggregation) {
			this.columnName = columnName;
			this.columnAlias = columnAlias;
			this.columnAggregation = columnAggregation;
		}

		public UserPhoneTab alias(String alias) {
			this.columnAlias = alias;
			return this;
		}

		public UserPhoneTab aggregate(Aggregation agg) {
			this.columnAggregation = agg;
			return this;
		}

		public String getColumnName() {
			if (columnAlias == null && columnAggregation == null) {
				return getTableName() + "." + columnName;
			} else if (columnAlias == null) {
				return columnAggregation + "(" + getTableName() + "." + columnName + ")";
			} else if (columnAggregation == null) {
				return getTableName() + "." + columnName + " AS " + columnAlias;
			} else {
				return columnAggregation.toString().equals("GROUP_CONCAT")
						? columnAggregation + "(DISTINCT " + getTableName() + "." + columnName + ") AS " + columnAlias
						: columnAggregation + "(" + getTableName() + "." + columnName + ") AS " + columnAlias;
			}
		}

		public String getTableName() {
			return JojoDB.Table.USERPHONE.getTableName();
		}

		public String getTableAlias() {
			return JojoDB.Table.USERPHONE.getTableAlias();
		}

		public String getColumnAlias() {
			return columnAlias == null ? "" : columnAlias;
		}

		public String getColumnAggregation() {
			return columnAggregation == null ? "" : columnAggregation.toString();
		}
	}

}
