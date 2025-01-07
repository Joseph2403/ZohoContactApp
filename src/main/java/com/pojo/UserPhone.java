package com.pojo;

public class UserPhone {
	private Long userId;
	private Long userPhone;
	
	public UserPhone() {
		
	}
	
	public UserPhone(Long userId, Long userPhone) {
		this.userId = userId;
		this.userPhone = userPhone;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
	
	
}
