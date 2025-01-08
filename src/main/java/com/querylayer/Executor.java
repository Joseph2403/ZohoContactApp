package com.querylayer;

import java.util.*;

import com.pojo.*;

import java.sql.*;
import java.lang.reflect.*;

public class Executor {
	private static String url = "jdbc:mysql://localhost:3306/Jojo";
	private static String user = "root";
	private static String password = "root";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	public static Connection connectToDB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static SelectExecutor executeSelect(ResultSet rs, Class<?> clazz) {
		return new SelectExecutor(rs, clazz);
	}

	public static class SelectExecutor {
		private String query;
		private List<Object> result = new ArrayList<>();
		Class<?> clazz;
//		private QueryBuilder2 qb;
		private ResultSet rs;

		public SelectExecutor(ResultSet rs, Class<?> clasz) {
//			this.query = query;
			this.clazz = clasz;
			this.rs = rs;
		}

//		public <T> List<T> execute(Class<T> clazz) throws InstantiationException, IllegalAccessException,
//				IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
//				ClassNotFoundException, SQLException {
//			Connection conn = connectToDB();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(qb.finalQuery);
////			HashMap<String, Object> pojos = new HashMap<>();
//			ResultSetMetaData metaData = rs.getMetaData();
//			T pojo = clazz.getDeclaredConstructor().newInstance();
//			for (Field field : clazz.getDeclaredFields()) {
//				field.setAccessible(true);
//				if (field.getType().equals(ArrayList.class)) {
//					ParameterizedType listType = (ParameterizedType) field.getGenericType();
//					Type type = listType.getActualTypeArguments()[0];
//					Class<?> typeClass = Class.forName(type.getTypeName());
//					field.set(pojo, execute(typeClass));
//				} else {
//					try {
//						field.set(pojo, rs.getObject(field.getName()));
//					} catch (SQLException e) {
//						field.set(pojo, null);
//					}
//				}
//			}
//			return null;
//		}

//		public <T> List<T> manishaGen(ResultSet rs, Class<T> clazz, Object check) 
//		        throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
//		               InvocationTargetException, NoSuchMethodException, SecurityException, 
//		               ClassNotFoundException, SQLException {
//		    
//		    List<T> resultList = new ArrayList<>();
//		    int fieldCount = clazz.getDeclaredFields().length;
//
//		    if (check == null) {
//		        // Outer loop to process main rows
//		        while (rs.next()) {
//		            T pojo = clazz.getDeclaredConstructor().newInstance();
//		            System.out.println(clazz + " Checked: Row = " + rs.getRow());
//
//		            // Populate fields
//		            for (Field field : clazz.getDeclaredFields()) {
//		                field.setAccessible(true);
//		                try {
//		                    if (field.getType().equals(ArrayList.class)) {
//		                        ParameterizedType listType = (ParameterizedType) field.getGenericType();
//		                        Type type = listType.getActualTypeArguments()[0];
//		                        Class<?> arrayType = Class.forName(type.getTypeName());
//
//		                        // Handle nested objects
//		                        int currentRow = rs.getRow(); // Save current cursor position
//		                        Object checker = rs.getObject(1);
//		                        field.set(pojo, manishaGen(rs, arrayType, checker));
//		                        while (rs.getObject(1).equals(checker)) {
//		                        	rs.next();
//		                        }
//		                        rs.absolute(currentRow); // Restore cursor position
//		                    } else {
//		                        field.set(pojo, rs.getObject(field.getName()));
//		                    }
//		                } catch (Exception e) {
//		                    field.set(pojo, null); // Handle missing fields gracefully
//		                }
//		            }
//
//		            resultList.add(pojo); // Add populated object to the list
//		        }
//		    } else {
//		        // Nested loop for recursive processing
//		        int currentRow = rs.getRow(); // Save starting position
//		        do {
//		        	if (!rs.getObject(1).equals(check)) {
//		        		break;
//		        	}
//		            T pojo = clazz.getDeclaredConstructor().newInstance();
//		            System.out.println(clazz + " Inner Checked: Row = " + rs.getRow());
//
//		            for (Field field : clazz.getDeclaredFields()) {
//		                field.setAccessible(true);
//		                try {
//		                    if (field.getType().equals(ArrayList.class)) {
//		                        ParameterizedType listType = (ParameterizedType) field.getGenericType();
//		                        Type type = listType.getActualTypeArguments()[0];
//		                        Class<?> arrayType = Class.forName(type.getTypeName());
//		                        
//		                        // Handle nested objects recursively
//		                        field.set(pojo, manishaGen(rs, arrayType, rs.getObject(1)));
//		                    } else {
//		                        field.set(pojo, rs.getObject(field.getName()));
//		                    }
//		                } catch (Exception e) {
//		                    field.set(pojo, null);
//		                }
//		            }
//		            resultList.add(pojo); // Add populated object to the list
//		        } while (rs.next() && rs.getRow() != currentRow); // Stop if the cursor loops back
//		    }
//
//		    return resultList;
//		}

//		public <T> List<T> generatePojo(ResultSet rs, Class<T> clazz, Long userId) throws InstantiationException,
//				IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
//				SecurityException, ClassNotFoundException, SQLException {
//			int count = 0;
//			List<T> result = new ArrayList<>();
//			while (rs.next()) {
//				T pojo = clazz.getDeclaredConstructor().newInstance();
//				System.out.println(clazz + " Checking");
//				for (Field field : clazz.getDeclaredFields()) {
//					field.setAccessible(true);
//
//					if (userId == null) {
//						if (field.getType().equals(ArrayList.class)) {
//							ParameterizedType listType = (ParameterizedType) field.getGenericType();
//							Type type = listType.getActualTypeArguments()[0];
//							Class<?> typeClass = Class.forName(type.getTypeName());
//							rs.beforeFirst();
//							field.set(pojo, generatePojo(rs, typeClass, null));
//						} else {
//							try {
//								field.set(pojo, rs.getObject(field.getName()));
//							} catch (SQLException e) {
//								field.set(pojo, null);
//							}
//						}
//					} else {
//
//					}
//
//				}
//				result.add(pojo);
//			}
//			return result;
//		}

//		public <T> List<T> demoExecute(ResultSet rs, QueryBuilder2 qb, Class<T> clazz) throws SQLException {
//		    List<T> resultList = new ArrayList<>();
//		    ResultSetMetaData metaData = rs.getMetaData();
//		    int colCount = metaData.getColumnCount();
//		    String key;
//		    String mainTable = qb.mainTable;
//		    
//		    while (rs.next()) {
//		        key = "";
//		        for (int i = 1; i <= colCount; i++) {
//		            if (metaData.getTableName(i).equals(mainTable)) {
//		                // Safely convert the value to a String
//		                key += String.valueOf(rs.getObject(i)); // Or rs.getString(i)
//		            }
//		        }
//		        System.out.println(key+"\n");
//		    }
//		    return resultList;
//		}


		public <T> List<T> execute(ResultSet rs, QueryBuilder2 qb, Class<T> clazz)
				throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
				InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
			List<T> resultList = new ArrayList<>();
			HashMap<String, T> pojos = new HashMap<>();
			ResultSetMetaData metaData = rs.getMetaData();
			String mainTable = qb.mainTable;
			int colCount = (int) Math.ceil(metaData.getColumnCount() / 5);
			String key;
			while (rs.next()) {
				T pojo;
				key = "";
				for (int i = 1; i <= colCount; i++) {
					if (metaData.getTableName(i).equals(mainTable)) {
						key += String.valueOf(rs.getObject(i));
					}
				}
				pojo = pojos.get(key);
				if (pojo == null) {
					pojo = clazz.getDeclaredConstructor().newInstance();
					pojos.put(key, pojo);
				}
				
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);

					if (field.getType().equals(ArrayList.class)) {
						ParameterizedType listType = (ParameterizedType) field.getGenericType();
						Type type = listType.getActualTypeArguments()[0];
						Class<T> typeClass = (Class<T>) Class.forName(type.getTypeName());
						T pojo2 =  typeClass.getDeclaredConstructor().newInstance();
						ArrayList<T> pojoList = (ArrayList<T>) field.get(pojo);
						for (Field field2 : typeClass.getDeclaredFields()) {
							field2.setAccessible(true);
							try {
								field2.set(pojo2, rs.getObject(field2.getName()));
							} catch (SQLException e) {
								field2.set(pojo2, null);
							}
						}
						if (!pojoList.contains(pojo2)) {
							pojoList.add(pojo2);
						}

						field.set(pojo, pojoList);

					}

					else {
						try {
							field.set(pojo, rs.getObject(field.getName()));
						} catch (SQLException e) {
							field.set(pojo, null);
						}
					}
				}
			}
			for (T value: pojos.values()) {
				resultList.add(value);
			}
			return resultList;
		}
		
//		public <T> List<T> execute() throws ClassNotFoundException, SQLException, InstantiationException,
//				IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
//				SecurityException, NoSuchFieldException {
////			return (List<T>) generatePojo(this.rs, this.clazz);
//
//			while (this.rs.next()) {
//				T pojo = (T) clazz.getDeclaredConstructor().newInstance();
//
//				for (Field field : clazz.getDeclaredFields()) {
//					field.setAccessible(true);
//
//					if (field.getType().equals(ArrayList.class)) {
//						ParameterizedType listType = (ParameterizedType) field.getGenericType();
//						Type type = listType.getActualTypeArguments()[0];
//						Class<?> typeClass = Class.forName(type.getTypeName());
//						Object pojo2 = typeClass.getDeclaredConstructor().newInstance();
//						ArrayList<Object> list = new ArrayList<>();
//						for (Field field2 : typeClass.getDeclaredFields()) {
//							field2.setAccessible(true);
//							try {
//								field2.set(pojo2, rs.getObject(field2.getName()));
//							} catch (SQLException e) {
//								field2.set(pojo2, null);
//							}
//						}
//						list.add(pojo2);
//
//						field.set(pojo, list);
//
//					}
//
//					else {
//						try {
//							field.set(pojo, rs.getObject(field.getName()));
//						} catch (SQLException e) {
//							field.set(pojo, null);
//						}
//					}
//				}
//
////				for (int i = 1; i <= metaData.getColumnCount(); i++) {
////					Field field = clazz.getDeclaredField(metaData.getColumnName(i));
////					field.setAccessible(true);
////
////					if (field.getType().equals(ArrayList.class)) {
////						ParameterizedType listType = (ParameterizedType) field.getGenericType();
////						Type type = listType.getActualTypeArguments()[0];
////						Class<?> typeClass = Class.forName(type.getTypeName());
////						Object newObj2 = typeClass.getDeclaredConstructor().newInstance();
////						ArrayList<Object> list = new ArrayList<>();
////
////					} else {
////						field.set(newObj, rs.getObject(metaData.getColumnName(i)));
////					}
////				}
//				result.add(pojo);
//			}
//
////		while (rs.next()) {
////			rows = new HashMap<>();
////			for (int i = 1; i <= metaData.getColumnCount(); i++) {
////				rows.put(metaData.getTableName(i) +"."+ metaData.getColumnLabel(i), rs.getObject(i));
////			}
////			columns.add(rows);
////		}
////		return columns;
////		}
//			return (List<T>) result;
//		}

		public static class InsertExecutor {
//		private String query;
		}
	}
}
