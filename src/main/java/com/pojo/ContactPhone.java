package com.pojo;

import java.util.Objects;

public class ContactPhone {
    private Long contactId;
    private Long contactPhone;

    // Default constructor
    public ContactPhone() {
    }

    // Parameterized constructor
    public ContactPhone(Long contactId, Long contactPhone) {
        this.contactId = contactId;
        this.contactPhone = contactPhone;
    }

    // Getters and Setters
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Long contactPhone) {
        this.contactPhone = contactPhone;
    }

    // Override equals to compare ContactPhone objects by field values
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPhone that = (ContactPhone) o;
        return Objects.equals(contactId, that.contactId) &&
                Objects.equals(contactPhone, that.contactPhone);
    }

    // Override hashCode to ensure consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(contactId, contactPhone);
    }

    // Override toString for better readability when printing ContactPhone objects
    @Override
    public String toString() {
        return "ContactPhone{" +
                "contactId=" + contactId +
                ", contactPhone=" + contactPhone +
                '}';
    }
}