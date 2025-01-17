<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.ContactDao, com.pojo.*, java.util.ArrayList" %>
<% 
long contactId = (long) request.getAttribute("contactId");
Contact contact = (Contact) ContactDao.getContactPojo(contactId);
ArrayList<ContactEmail> emails = contact.getContactEmail();
ArrayList<ContactPhone> phones = contact.getContactPhone();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Details</title>
</head>
<body>
<% 
if (contact != null) {
%>
<h1>Contact Details</h1>
<h2>General Info</h2>
Name:<%= contact.getName() %><br>
Age:<%= contact.getAge() %><br>
DOB:<%= contact.getDateOfBirth() %><br>
State:<%= contact.getState() %><br>
City:<%= contact.getCity() %><br>
<br>
<h2>Emails</h2>
<%
for (ContactEmail email: emails) {
%>
<p><%= email.getContactEmail() %></p>
<%
}
%>
<br>
<h2>Phone Numbers</h2>
<%
for (ContactPhone phone: phones) {
%>
<p><%= phone.getContactPhone() %></p>
<%
}
%>
<a href="contacts.jsp">Back</a>
<%
} else {
%>
<p>Invalid Contact</p>
<% } %>
</body>
</html>