package com.servlets;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONObject;

import java.time.*;

import com.pojo.User;

public class javatest {
	public static void main(String args[]) {
		String jsonString = "{\"name\":\"Christopher\",\"age\":20,\"city\":\"Chennai\"}";

		JSONObject jsonObject = new JSONObject(jsonString);

		HashMap<String, Object> map = new HashMap<>();
		for (Object key : jsonObject.keySet()) {
			map.put((String) key, jsonObject.get((String) key));
		}

		System.out.println(map.get("age"));
	}
}
