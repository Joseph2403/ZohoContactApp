package com.pojo;

public class UserEmail {
	String email;
	boolean isPrime;
	
	public UserEmail(String email, boolean isPrime) {
		this.email = email;
		this.isPrime = isPrime;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isPrime() {
		return isPrime;
	}
	public void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}
	
	
}
