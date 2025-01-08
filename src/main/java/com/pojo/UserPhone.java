package com.pojo;

import java.util.Objects;

public class UserPhone {
    private Long userId;
    private Long userPhone;

    // Default constructor
    public UserPhone() {
    }

    // Parameterized constructor
    public UserPhone(Long userId, Long userPhone) {
        this.userId = userId;
        this.userPhone = userPhone;
    }

    // Getters and Setters
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

    // Override equals to compare UserPhone objects by field values
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPhone userPhone1 = (UserPhone) o;
        return Objects.equals(userId, userPhone1.userId) &&
               Objects.equals(userPhone, userPhone1.userPhone);
    }

    // Override hashCode to ensure consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(userId, userPhone);
    }

    // Override toString for better readability when printing UserPhone objects
    @Override
    public String toString() {
        return "UserPhone{" +
                "userId=" + userId +
                ", userPhone=" + userPhone +
                '}';
    }
}
