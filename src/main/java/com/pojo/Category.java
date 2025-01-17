package com.pojo;

public class Category {
	private Long categoryId;
	private Long userId;
	private String categoryName;
//	public ArrayList<Contact> categoryContacts = new ArrayList<>();

	public Category() {

	}

	public Category(Long categoryId, Long userId, String name) {
		this.categoryId = categoryId;
		this.userId = userId;
		this.categoryName = name;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Long getUserId() {
		return userId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
