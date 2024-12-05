package com.pojo;
import java.util.ArrayList;
import com.pojo.UserEmail;

public class User {
	public long userId;
	public String password;
	public String name;
	public String dateOfBirth;
	public int age;
	public String state;
	public String city;
	public ArrayList<Long> userPhone = new ArrayList<>();
	public ArrayList<UserEmail> userEmail = new ArrayList<>();
	
	public User(String password, String name, String dateOfBirth, int age, String state, String city) {
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.state = state;
		this.city = city;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public ArrayList<Long> getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(ArrayList<Long> userPhone) {
		this.userPhone = userPhone;
	}
	public ArrayList<UserEmail> getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(ArrayList<UserEmail> userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
