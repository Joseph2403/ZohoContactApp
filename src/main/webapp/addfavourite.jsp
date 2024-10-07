<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.ContactDao" %>
<% 
long userId = (long) session.getAttribute("userId");
ResultSet rs = ContactDao.getContacts(userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Favourite Adder</title>
</head>
<body>
<form action="AddFavouriteServlet" method="post">
<% while (rs.next()) { %>
	<input type="checkbox" id="<%= rs.getLong("contactId") %>" name="fav" value="<%= rs.getLong("contactId") %>" >
	<label for="<%= rs.getLong("contactId") %>"><%= rs.getString("name") %></label><br>
<% } %>
<input type="submit" value="Add Favourites" >
</form>
</body>
</html>