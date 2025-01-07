package com.servlets;

import java.lang.reflect.*;

import com.pojo.User;

public class javatest {
	public static void main (String args[]) {
		Class<?> clazz = User.class;
		for (Field field: clazz.getDeclaredFields()) {
			field.setAccessible(true);
			
			System.out.println(field.getName());
		}	
	}
}
