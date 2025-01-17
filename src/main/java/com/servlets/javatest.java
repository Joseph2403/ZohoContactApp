package com.servlets;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.time.*;

import com.pojo.User;

public class javatest {
	public static void main (String args[]) {
		Date date = new Date();
		System.out.println("Current date and time: "+ date.getTime());
		System.out.println("Current date and time 3: "+System.currentTimeMillis());
		System.out.println("Current date and time 6: "+Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		
	}
}
