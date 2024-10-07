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
<title>Contacts</title>
<script>
    function getClickedButtonName(button, event) {
        event.preventDefault();
        
        document.getElementById("contactId").value = button.name;

        document.myForm.submit();
    }
</script>
</head>
<body>
<% if (rs.next()) { %>
<form name="myForm"action="DisplayContactDetailsServlet" method="post">
<table>
<% 
do {
%>  
<tr>
<td>
    <input type="submit" name="<%= rs.getLong("contactId") %>" value="<%= rs.getString("name") %>" onclick="getClickedButtonName(this, event)">
</td>
</tr>
<%
} while (rs.next());
%>
</table>
<input type="hidden" value="" name="contactId" id="contactId">
</form>
<% } else { %>
<p>There was some error</p>
<% } %>
<a href="userdashboard.jsp">Back</a>
</body>
</html>
