package com.pojo;
public class UserEmail {
	private String userEmail;
	private Boolean isPrime;
	private Long userId;
	
	public UserEmail() {
		
	}
	
	public UserEmail(Long userId, String email, Boolean isPrime) {
		this.userId = userId;
		this.userEmail = email;
		this.isPrime = isPrime;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Boolean getIsPrime() {
		return isPrime;
	}
	public void setIsPrime(Boolean isPrime) {
		this.isPrime = isPrime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}