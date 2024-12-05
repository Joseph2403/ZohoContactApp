<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.database.*, com.pojo.*, java.util.*" %>
<% 
long userId = (long) session.getAttribute("userId");
User user = UserDao.getUserPojo(userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit User Details</title>
</head>
<body>
<form action="EditUserDetailsServlet" method="post">
        <table>
            <tr>
                <td><label>Name:</label></td>
                <td><input type="text" name="name" placeholder="Enter your Name" value="<%= user.getName() %>" required></td>
            </tr>
            <tr>
                <td><label>Date of Birth:</label></td>
                <td><input type="date" name="dateOfBirth" value="<%= user.getDateOfBirth() %>" placeholder="Enter your Date of Birth"></td>
            </tr>
            <tr>
                <td><label>Age:</label></td>
                <td><input type="text" name="age" value="<%= user.getAge() %>" placeholder="Enter your Age"></td>
            </tr>
            <tr>
                <td><label>State:</label></td>
                <td><input type="text" name="state" value="<%= user.getState() %>" placeholder="Enter your State"></td>
            </tr>
            <tr>
                <td><label>City:</label></td>
                <td><input type="text" name="city" value="<%= user.getCity() %>" placeholder="Enter your City"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Edit Details"></td>
            </tr>
        </table>
    </form>
</body>
</html>