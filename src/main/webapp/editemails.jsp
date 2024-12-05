<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.UserDao" %>
<% 
	long userId = (long) session.getAttribute("userId");
	ResultSet emails = UserDao.getUserEmailDetails(userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Emails</title>
</head>
<body>
<form name="myForm"action="PrimaryEmailServlet" method="post">
<% 
while (emails.next()) {
%>
<%= emails.getString("userEmail") %>
<input type="radio" name="primaryEmail" value="<%= emails.getString("userEmail") %>"
<% if (emails.getBoolean("isPrime")) { %>
checked
<% } %>
><br><br>
<%
}
%>
<input type="submit" value="Confirm" >
</form>
<a href="userdashboard.jsp">Back</a>
</body>
</html>