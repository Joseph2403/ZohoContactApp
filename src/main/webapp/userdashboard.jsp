<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.*, com.pojo.*, java.util.*" %>
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
if (session.getAttribute("userId") != null) {
	long userId = (long) session.getAttribute("userId");
	User user = UserDao.getUserPojo(userId);
	ArrayList<UserEmail> emails = user.getUserEmail();
	ArrayList<Long> phones = user.getUserPhone();
if (user != null) {
ArrayList<Category> categories = CategoryDao.getCategories(userId);
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
    document.myEmailForm.submit();
}

function deleteClickedPhone(button, event) {
	event.preventDefault();
	document.getElementById("delPhone").value = button.name;
    document.myPhoneForm.submit();
}

function addEmail() {
	if (emailCount < 5) {
    emailCount++;
    const emailContainer = document.getElementById('contactEmailContainer');
    const newDiv = document.createElement('div');
    newDiv.id = 'email'+emailCount;
    const newEmailInput = document.createElement('input');
    newEmailInput.type = 'text';
    newEmailInput.name = 'contactEmail';
    newEmailInput.placeholder = 'Enter Email Address ' + emailCount;
    newDiv.appendChild(newEmailInput);
    emailContainer.appendChild(newDiv);
	} else {
		return;
	}
}

function addPhone() {
	if (phoneCount < 5) {
    phoneCount++;
    const phoneContainer = document.getElementById('contactPhoneContainer');
    const newDiv = document.createElement('div');
    newDiv.id = 'phone'+phoneCount;
    const newPhoneInput = document.createElement('input');
    newPhoneInput.type = 'text';
    newPhoneInput.name = 'contactPhone';
    newPhoneInput.placeholder = 'Enter Phone Number ' + phoneCount;
    newDiv.appendChild(newPhoneInput);
    phoneContainer.appendChild(newDiv);
	} else {
		return;
	}
}

function removeEmail() {
	if (emailCount > 1) {
	    const emailContainer = document.getElementById('contactEmailContainer');
	    const emailToRemove = document.getElementById('email'+emailCount);
	    emailContainer.removeChild(emailToRemove);
	    emailCount--;
	} else {
		return;
	}
}

function removePhone() {
	if (phoneCount > 1) {
	    const phoneContainer = document.getElementById('contactPhoneContainer');
	    const phoneToRemove = document.getElementById('phone'+phoneCount);
	    phoneContainer.removeChild(phoneToRemove);
	    phoneCount--;
	} else {
		return;
	}
}



</script>

</head>
<body>
<h1><%= user.getName() %>'s Dashboard !!!</h1>
<a href="edituser.jsp">Edit User Details</a>
<div id="profile">
<h2>Profile</h2>
<p>Name: <%= user.getName() %></p>
<p>Age: <%= user.getAge() %></p>
<p>DOB: <%= user.getDateOfBirth() %></p>
<p>📍<%= user.getState() %>, <%= user.getCity() %></p>

<table>
<tr>
<td><h3 style="margin-right:5px;">Emails</h3></td>
<td><a href="editemails.jsp"><input type="button" id="editPrimaryEmail" value="Set Primary Email"></a></td>
</tr>
</table>

<form name="myEmailForm"action="DeleteEmailServlet" method="post">
<%  
for (UserEmail email: emails) {
%>
<%= email.getEmail() %>
<% if (email.isPrime()) { %>
 (PRIMARY)
<% } %>
<input type="submit" name="<%= email.getEmail() %>" value="❌" onclick="deleteClickedEmail(this, event)">
<br><br>
<%
}
%>
<input type="hidden" value="" name="delEmail" id="delEmail">
</form>

<form name="myPhoneForm"action="DeletePhoneServlet" method="post">
<h3>Phone Numbers</h3>
<% 
for (Long phone: phones) {
%>
<div>
<%= phone %>
<input type="submit" name="<%= phone %>" value="❌" onclick="deleteClickedPhone(this, event)"></div><br>
<%
}
%>
<input type="hidden" value="" name="delPhone" id="delPhone">
</form>
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
<input type="text" name="contactPhone" placeholder="Enter Phone Number 1" id="phone1" required><button type="button" onclick="addPhone()">+</button><button type="button" onclick="removePhone()">-</button>
</div>
<br>
<label>Email Id:</label>
<div id="contactEmailContainer">
<input type="email" name="contactEmail" placeholder="Enter Email Address 1" id="email1" required><button type="button" onclick="addEmail()">+</button><button type="button" onclick="removeEmail()">-</button>

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
<!-- 
<h2>Favourites Management</h2>
<a href="addfavourite.jsp">Add Favourite</a>
<a href="favourites.jsp">Show Favourites</a>
<br>
<hr>
 -->
<h2>Category Management</h2>
<form action="CreateGroupServlet" method="post">
<input type="text" name="categoryName" placeholder="Enter Category Name">
<input type="submit" value="create Category">
</form>

<h3>LIST OF CATEGORIES</h3>
<%
if (categories != null) {
for (Category category: categories) { %>
<a href="viewcategory.jsp?categoryId=<%= category.getCategoryId() %>&categoryName=<%= category.getCategoryName() %>">View <%= category.getCategoryName() %></a> - <a href="DeleteCategoryServlet?categoryId=<%= category.getCategoryId() %>">Delete <%= category.getCategoryName() %></a>
<br>
<% }
} else {
	%>
	<p>No Categories Yet</p><br>
	<%
}
%>	
</body>
</html>

<%
}
} else {
	response.sendRedirect("login.jsp");
}
%>