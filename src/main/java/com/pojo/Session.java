package com.pojo;

public class Session {
	private String sessionId;
	private Long userId;
	private Long createdAt;
	private Long lastAccessed;
	private Long expiresAt;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public Long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}
}
