<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.database.ContactDao, java.util.*, com.pojo.*" %>
<% 
long userId = (long) session.getAttribute("userId");
ArrayList<Contact> contacts = ContactDao.getUserContactPojo(userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contacts</title>
<script>
    function submitFormWithContactId(button, action) {
        event.preventDefault();
        
        document.getElementById("contactId").value = button.name;
        document.getElementById("deleteId").value = '';
        
        document.myForm.action = action;
        document.myForm.submit();
    }
    function submitFormWithDeleteId(button, action) {
        event.preventDefault();
        
        document.getElementById("deleteId").value = button.name;
        document.getElementById("contactId").value = '';

        document.myForm.action = action;
        document.myForm.submit();
    }
</script>
</head>
<body>
<% if (contacts != null) { %>
<form name="myForm"action="DisplayContactDetailsServlet" method="post">
<table>
<% 
for (Contact contact: contacts) {
%>  
<tr>
<td>
    <input type="submit" name="<%= contact.getContactId() %>" value="<%= contact.getName() %>" onclick="submitFormWithContactId(this, 'DisplayContactDetailsServlet')">
</td>
<td>
	<input type="submit" name="<%= contact.getContactId() %>" value="Delete <%= contact.getName() %>" onclick="submitFormWithDeleteId(this, 'DeleteContactServlet')">
</td>
</tr>
<%
}
%>
</table>
<input type="hidden" value="" name="contactId" id="contactId">
<input type="hidden" value="" name="deleteId" id="deleteId">
</form>
<% } else { %>
<p>No Contacts to Display</p>
<% } %>
<a href="userdashboard.jsp">Back</a>
</body>
</html>
