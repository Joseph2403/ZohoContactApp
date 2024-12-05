package com.pojo;

import java.util.ArrayList;

public class Category {
	public long categoryId;
	public String categoryName;
	public ArrayList<Contact> categoryContacts = new ArrayList<>();
	
	public Category (String name) {
		this.categoryName = name;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<Contact> getCategoryContacts() {
		return categoryContacts;
	}

	public void setCategoryContacts(ArrayList<Contact> categoryContacts) {
		this.categoryContacts = categoryContacts;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
