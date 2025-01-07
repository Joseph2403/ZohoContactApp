package com.pojo;

import java.util.ArrayList;

public class Contact {
	public long contactId;
	public long userId;
	public String name;
	public int age;
	public String dateOfBirth;
	public String state;
	public String city;
	ArrayList<String> contactEmail = new ArrayList<>();
	ArrayList<Long> contactPhone = new ArrayList<>();
	public Contact () {
		
	}
 	public Contact(String name, int age, String dateOfBirth, String state, String city) {
		this.name  = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.state = state;
		this.city = city;
	}
	
	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ArrayList<String> getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(ArrayList<String> contactEmail) {
		this.contactEmail = contactEmail;
	}

	public ArrayList<Long> getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(ArrayList<Long> contactPhone) {
		this.contactPhone = contactPhone;
	}

	
}
