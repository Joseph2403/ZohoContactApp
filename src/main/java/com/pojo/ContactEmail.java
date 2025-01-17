package com.pojo;

import java.util.Objects;

public class ContactEmail {
    private Long contactId;
    private String contactEmail;

    // Default constructor
    public ContactEmail() {
    }

    // Parameterized constructor
    public ContactEmail(Long contactId, String contactEmail) {
        this.contactId = contactId;
        this.contactEmail = contactEmail;
    }

    // Getters and Setters
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    // Override equals to compare ContactEmail objects by field values
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEmail that = (ContactEmail) o;
        return Objects.equals(contactId, that.contactId) &&
                Objects.equals(contactEmail, that.contactEmail);
    }

    // Override hashCode to ensure consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(contactId, contactEmail);
    }

    // Override toString for better readability when printing ContactEmail objects
    @Override
    public String toString() {
        return "ContactEmail{" +
                "contactId=" + contactId +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}