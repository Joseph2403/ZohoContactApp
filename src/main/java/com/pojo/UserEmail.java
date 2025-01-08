package com.pojo;

import java.util.Objects;

public class UserEmail {
    private String userEmail;
    private Boolean isPrime;
    private Long userId;

    // Default constructor
    public UserEmail() {
    }

    // Parameterized constructor
    public UserEmail(Long userId, String email, Boolean isPrime) {
        this.userId = userId;
        this.userEmail = email;
        this.isPrime = isPrime;
    }

    // Getters and Setters
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

    // Override equals to compare UserEmail objects by field values
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail1 = (UserEmail) o;
        return Objects.equals(userEmail, userEmail1.userEmail) &&
                Objects.equals(isPrime, userEmail1.isPrime) &&
                Objects.equals(userId, userEmail1.userId);
    }

    // Override hashCode to ensure consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(userEmail, isPrime, userId);
    }

    // Override toString for better readability when printing UserEmail objects
    @Override
    public String toString() {
        return "UserEmail{" +
                "userEmail='" + userEmail + '\'' +
                ", isPrime=" + isPrime +
                ", userId=" + userId +
                '}';
    }
}
