<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.UserDao" %>
<% 
long userId = (long) session.getAttribute("userId");
ResultSet rs = (ResultSet) session.getAttribute("result");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<script>
let emailCount = 1;
let phoneCount = 1;

function addEmail() {
    emailCount++;
    const emailContainer = document.getElementById('contactEmailContainer');
    const newEmailInput = document.createElement('input');
    newEmailInput.type = 'text';
    newEmailInput.name = 'contactEmail' + emailCount;
    newEmailInput.placeholder = 'Enter Email Address ' + emailCount;
    emailContainer.append(document.createElement('br'));
    emailContainer.appendChild(newEmailInput);
}

function addPhone() {
    phoneCount++;
    const phoneContainer = document.getElementById('contactPhoneContainer');
    const newPhoneInput = document.createElement('input');
    newPhoneInput.type = 'text';
    newPhoneInput.name = 'contactPhone' + phoneCount;
    newPhoneInput.placeholder = 'Enter Phone Number ' + phoneCount;
    phoneContainer.append(document.createElement('br'));
    phoneContainer.append(newPhoneInput);
}
</script>
</head>
<body>
<h1><%= rs.getString("name") %>'s Dashboard !!!</h1>
<h2>Profile</h2>
<p>Name: <%= rs.getString("name") %></p>
<p>Age: <%= rs.getInt("age") %></p>
<p>DOB: <%= rs.getString("dateOfBirth") %></p>
<p>📍<%= rs.getString("state") %>, <%= rs.getString("city") %></p>
<hr>
<h2>Additional Email and Phone Number</h2>
<table>
        <tr>
            <td>
                <form action="AddEmailServlet" method="post">
                    <p>Enter Email id</p>
                    <input type="text" name="addemail"><br><br>
                    <input type="submit" value="Add Email">
                    
                </form>
            </td>
            <td>
                <form action="AddPhoneServlet" method="post">
                    <p>Enter Phone Number</p>
                    <input type="text" name="addphone"><br><br>
                    <input type="submit" value="Add Number" >
                </form>
            </td>
        </tr>
    </table>
<hr>
<h2>Add Contact</h2>
<form action="AddContactServlet" method="post">
<label>Name:</label>
<input type="text" name="name" placeholder="Enter your Name" required><br>
<label>Date of Birth:</label>
<input type="date" name="dateOfBirth" placeholder="Enter your Date of Birth" required><br>
<label>Age:</label>
<input type="text" name="age" placeholder="Enter your Age" required><br>
<label>State:</label>
<input type="text" name="state" placeholder="Enter your State" required><br>
<label>City:</label>
<input type="text" name="city" placeholder="Enter your City" required><br>
<label>Phone Number:</label>
<div id="contactPhoneContainer">
<input type="text" name="contactPhone1" placeholder="Enter Phone Number 1"" required>
</div>
<button type="button" onclick="addPhone()">Add Phone Number</button>
<label>Email Id:</label>
<div id="contactEmailContainer">
<input type="email" name="contactEmail1" placeholder="Enter Email Address 1" required>
</div>
<button type="button" onclick="addEmail()">Add Email Id</button>
<input type="submit" value="Add Contact">
</form>
</body>
</html>