package com.pojo;
import java.util.ArrayList;
import com.pojo.UserEmail;

public class User {
	public Long userId;
	public String password;
	public String name;
	public String dateOfBirth;
	public Integer age;
	public String state;
	public String city;
	public Integer passCheck;
	public ArrayList<UserEmail> userEmail = new ArrayList<>();
	public ArrayList<UserPhone> userPhone = new ArrayList<>();
	
	
	public User() {
		
	}
	
	public User(String password, String name, String dateOfBirth, int age, String state, String city, int passCheck) {
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.state = state;
		this.city = city;
		this.passCheck = passCheck;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
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

	public Integer getPassCheck() {
		return passCheck;
	}

	public void setPassCheck(Integer passCheck) {
		this.passCheck = passCheck;
	}

	public ArrayList<UserPhone> getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(ArrayList<UserPhone> userPhone) {
		this.userPhone = userPhone;
	}

	public ArrayList<UserEmail> getUserEmail() {
		return userEmail;
	}

	public void setUserEmail( ArrayList<UserEmail> userEmail) {
		this.userEmail = userEmail;
	}

	
	
}
