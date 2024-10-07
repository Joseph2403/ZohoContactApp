<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.ContactDao" %>
<% 
long contactId = (long) session.getAttribute("contactId");
ResultSet rs = ContactDao.getContactDetails(contactId);
ResultSet emails = ContactDao.getContactEmail(contactId);
ResultSet phones = ContactDao.getContactPhone(contactId);
rs.next();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Details</title>
</head>
<body>
<h1>Contact Details</h1>
<h2>General Info</h2>
Name:<%= rs.getString("name") %><br>
Age:<%= rs.getInt("age") %><br>
DOB:<%= rs.getString("dateOfBirth") %><br>
State:<%= rs.getString("state") %><br>
City:<%= rs.getString("city") %><br>
<br>
<h2>Emails</h2>
<%
while (emails.next()) {
%>
<p><%= emails.getString("contactEmail") %></p>
<%
}
%>
<br>
<h2>Phone Numbers</h2>
<%
while (phones.next()) {
%>
<p><%= phones.getString("contactPhone") %></p>
<%
}
%>
<a href="contacts.jsp">Back</a>
</body>
</html>