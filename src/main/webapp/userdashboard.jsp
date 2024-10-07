<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.UserDao" %>
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
if (session.getAttribute("userId") != null) {
	long userId = (long) session.getAttribute("userId");
	ResultSet rs = UserDao.getUserDetails(userId);
	ResultSet emails = UserDao.getUserEmailDetails(userId);
	ResultSet phones = UserDao.getUserPhoneDetails(userId);
	session.setAttribute("emails", emails);
if (rs.next()) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<script>

let emailCount = 1;
let phoneCount = 1;

function deleteClickedEmail(button, event) {
	event.preventDefault();
	document.getElementById("delEmail").value = button.name;
    document.myForm.submit();
}

function addEmail() {
	if (emailCount < 5) {
    emailCount++;
    const emailContainer = document.getElementById('contactEmailContainer');
    const newEmailInput = document.createElement('input');
    newEmailInput.type = 'text';
    newEmailInput.name = 'contactEmail' + emailCount;
    newEmailInput.placeholder = 'Enter Email Address ' + emailCount;
    emailContainer.appendChild(document.createElement('br'));
    emailContainer.appendChild(newEmailInput);
	} else {
		return;
	}
}

function addPhone() {
	if (phoneCount < 5) {
    phoneCount++;
    const phoneContainer = document.getElementById('contactPhoneContainer');
    const newPhoneInput = document.createElement('input');
    newPhoneInput.type = 'text';
    newPhoneInput.name = 'contactPhone' + phoneCount;
    newPhoneInput.placeholder = 'Enter Phone Number ' + phoneCount;
    phoneContainer.appendChild(document.createElement('br'));
    phoneContainer.appendChild(newPhoneInput);
	} else {
		return;
	}
}



</script>

</head>
<body>
<h1><%= rs.getString("name") %>'s Dashboard !!!</h1>
<div id="profile">
<h2>Profile</h2>
<p>Name: <%= rs.getString("name") %></p>
<p>Age: <%= rs.getInt("age") %></p>
<p>DOB: <%= rs.getString("dateOfBirth") %></p>
<p>📍<%= rs.getString("state") %>, <%= rs.getString("city") %></p>


<h3>Emails</h3><button onclick="sayHello()" id="openEmailAdder">AE</button>
<a href="editemails.jsp"><input type="button" id="editPrimaryEmail" value="EE"></a><br>
<form name="myForm"action="DeleteEmailServlet" method="post">
<% 
while (emails.next()) {
%>
<%= emails.getString("userEmail") %>
<% if (emails.getBoolean("isPrime")) { %>
 (PRIMARY)
<% } %>
<input type="submit" name="<%= emails.getString("userEmail") %>" value="❌" onclick="deleteClickedEmail(this, event)">
<br><br>
<%
}
%>
<input type="hidden" value="" name="delEmail" id="delEmail">
</form>
<h3>Phone Numbers</h3>
<% 
while (phones.next()) {
%>
<p><%= phones.getString("userPhone") %></p>
<%
}
%>
<hr>
</div>
<h2>Additional Email and Phone Number</h2>

    	<form action="AddEmailServlet" method="post">
	        <p>Enter Email id</p>
	        <input type="text" name="addemail"><br><br>
	        <input type="submit" value="Add Email">
        </form>
                <form action="AddPhoneServlet" method="post">
                    <p>Enter Phone Number</p>
                    <input type="text" name="addphone"><br><br>
                    <input type="submit" value="Add Number" >
                </form>

<hr>
<h2>Add Contact</h2>
<form action="AddContactServlet" method="post">
<label>Name:</label>
<input type="text" name="contName" placeholder="Enter your Name" required><br>
<label>Date of Birth:</label>
<input type="date" name="contDateOfBirth" placeholder="Enter your Date of Birth" required><br>
<label>Age:</label>
<input type="text" name="contAge" placeholder="Enter your Age" required><br>
<label>State:</label>
<input type="text" name="contState" placeholder="Enter your State" required><br>
<label>City:</label>
<input type="text" name="contCity" placeholder="Enter your City" required><br>
<label>Phone Number:</label>
<div id="contactPhoneContainer">
<input type="text" name="contactPhone1" placeholder="Enter Phone Number 1" required><button type="button" onclick="addPhone()">+</button>
</div>
<br>
<label>Email Id:</label>
<div id="contactEmailContainer">
<input type="email" name="contactEmail1" placeholder="Enter Email Address 1" required><button type="button" onclick="addEmail()">+</button>

</div>

<br><br>
<input type="submit" value="Add Contact"><br>
</form>
<form><input type="submit" value="Logout" formaction="LogoutServlet" formmethod="post"></form>
<br>
<hr>

<h2>Contact Management</h2>
<form>
<input type="submit" value="View Contacts" formaction="contacts.jsp" formmethod="post">
</form>
<br>
<hr>

<h2>Favourites Management</h2>
<a href="addfavourite.jsp">Add Favourite</a>
<a href="favourites.jsp">Show Favourites</a>

</body>
</html>

<%
} else {
	response.sendRedirect("login.jsp");
}
} else {
	response.sendRedirect("login.jsp");
}
%>