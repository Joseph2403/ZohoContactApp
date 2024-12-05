<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.*, com.pojo.*, java.util.*" %>
<%
long categoryId = Long.parseLong(request.getParameter("categoryId"));
String categoryName = request.getParameter("categoryName");
session.setAttribute("categoryId", categoryId);
session.setAttribute("categoryName", categoryName);
long userId = (long) session.getAttribute("userId");
ArrayList<Contact> newContacts = CategoryDao.getCategoryContacts(categoryId);
ArrayList<Contact> addedContacts = CategoryDao.getAddedCategoryContacts(categoryId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category</title>
<script>
    function submitFormWithContactId(button, action) {
        document.getElementById("contactId").value = button.name;
        document.getElementById("deleteId").value = '';  // Clear the deleteId field

        document.myForm.action = action;
        document.myForm.submit();
    }

    function submitFormWithDeleteId(button, action) {
        document.getElementById("deleteId").value = button.name;
        document.getElementById("contactId").value = '';  // Clear the contactId field

        document.myForm.action = action;
        document.myForm.submit();
    }
</script>
</head>
<body>
<% 
if (newContacts == null && addedContacts == null) {
%>
<p>Create some Contacts FIRST!!!</p>
<% } 
else {
%>
<h2><%= categoryName %>'s Contact List</h2>
<form name="myForm" method="post">
    <table>
        <%if (addedContacts != null) {
        	for (Contact contact: addedContacts) {%>
        <tr>
            <td>
                <input type="button" name="<%= contact.getContactId() %>" value="<%= contact.getName() %>"
                onclick="submitFormWithContactId(this, 'DisplayContactDetailsServlet')">
            </td>
            
            <td>
                <input type="button" name="<%= contact.getContactId() %>" value="Remove <%= contact.getName() %>"
                onclick="submitFormWithDeleteId(this, 'DeleteContactFromCategoryServlet')">
            </td>
        </tr>
        <% }
        }
        else { %>
        	<tr>
        	<td>No Contacts are added yet</td>
        	</tr>
        <% }
        %>
    </table>
    
    <input type="hidden" name="contactId" id="contactId">
    <input type="hidden" name="deleteId" id="deleteId">
</form>
<h2>Add Contact To this Category</h2>
<% 
if (newContacts != null) {
%>
<form action="AddContactToCategoryServlet" method="post">
<% 

for (Contact contact: newContacts) { %>
<input type="checkbox" id="<%= contact.getContactId() %>" name="cat" value="<%= contact.getContactId() %>" >
<label for="<%= contact.getContactId() %>"><%= contact.getName() %></label><br>
<% } %>
<br>
<input type="submit" value="Add to <%= categoryName %>" >

</form>
<% 
} else {
%>
<p>No other Contacts to add</p>
<%
}
%>
<br>
<%
}
%>
<a href="userdashboard.jsp">Back</a>

</body>
</html>