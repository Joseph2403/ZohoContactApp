package com.servlets;

import com.pojo.*;
import com.querylayer.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
//import java.util.*;
import java.util.ArrayList;

public class TestExecutor {
	public static void main(String args[]) throws ClassNotFoundException, SQLException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {

		QueryBuilder2 qb = new QueryBuilder2();
		qb.select().from(JojoDB.Table.USER)
				.join(JojoDB.JoinType.LEFT_JOIN, JojoDB.Table.USEREMAIL, JojoDB.UserTab.USERID,
						JojoDB.UserEmailTab.USERID)
				.join(JojoDB.JoinType.LEFT_JOIN, JojoDB.Table.USERPHONE, JojoDB.UserTab.USERID,
						JojoDB.UserPhoneTab.USERID)
				.where(JojoDB.UserTab.USERID, JojoDB.Comparisons.EQUALS, "15").build();
		
		JojoStmt js = new JojoStmt(qb);
		JojoResult jr = js.executeQuery();
		while (jr.next()) {
			User pojo = jr.getPojo();
			ArrayList<UserEmail> userEmails = pojo.getUserEmail();
			ArrayList<UserPhone> userPhones = pojo.getUserPhone();
			for (UserEmail userEmail: userEmails) {				
				System.out.println(userEmail.getUserEmail()+" - "+userEmail.getIsPrime());
			}
			System.out.println("-------------------------------------------");
			for (UserPhone userPhone: userPhones) {
				System.out.println(userPhone.getUserPhone());
			}
		}
		
		
//		QueryBuilder2 qb2 = new QueryBuilder2();
//		qb2.select().from(JojoDB.Table.CONTACT)
//				.join(JojoDB.JoinType.INNER_JOIN, JojoDB.Table.CONTACTEMAIL, JojoDB.ContactTab.CONTACTID,
//						JojoDB.ContactEmailTab.CONTACTID)
//				.where(JojoDB.ContactTab.USERID, JojoDB.Comparisons.EQUALS, "6").build();
		
//		JojoStmt js = new JojoStmt(qb2);
//		JojoResult jr = js.executeQuery();
//		while (jr.next()) {
//			Contact pojo = jr.getPojo();
//			System.out.println(pojo.userId + ", " + pojo.name + ", " + pojo.age + ", " + pojo.city);
//			ArrayList<ContactEmail> emails = pojo.getContactEmail();
//			for (ContactEmail ue : emails) {
//				System.out.print(ue.getContactEmail() + ", ");
//			}
//			System.out.println();
//			
////			for (UserEmail ue : emails) {
////				System.out.println(ue.getUserEmail() + ", " + ue.getIsPrime());
////			}
//		}

//		System.out.println(qb.finalQuery);

//		List<HashMap<String, Object>> columns = (List<HashMap<String, Object>>) Executor.executeSelect(
//				"SELECT * from User;")
//				.execute();
//		for (HashMap<String, Object> row : columns) {
//			System.out.println(row.get("User.name"));
//		}

//		JojoResult<UserEmail> rs = new JojoResult<UserEmail>("select * from UserEmail;", UserEmail.class);

//		JojoResult<User> rs2 = new JojoResult<User>("select * from User");

//		if(rs.next()) {
//			System.out.println(rs.getValue("User.name"));
//		}

//		while (rs.next()) {
//			UserEmail val = rs.getPojo();
//			if (val != null) {
//				System.out.println(val.getUserId()+", "+val.getUserEmail()+", "+val.isPrime());
//			}
//			else {
//				System.out.println("NULL STATE AT "+rs.getIndex());
//			}
//		}

//		System.out.println();
//		while (rs2.next()) {
//			User val = rs2.getPojo();
//			if (val != null) {
//				System.out.println(val.getName() + ", " + val.getAge() + ", " + val.getCity());
//			} else {
//				System.out.println("NULL STATE AT " + rs2.getIndex());
//			}
//		}

//		Integer num = 4;
//		int num2 = num;
//		System.out.print(num2);

//		Class<?> clazz = User.class;
//		Object newObj = clazz.getDeclaredConstructor().newInstance();
//		System.out.println(newObj.getClass());
	}
}
