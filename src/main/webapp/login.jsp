<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
</head>
<body>
    <form action="LoginServlet" method="post">
        <table>
            <tr>
                <td><label>Email id:</label></td>
                <td><input type="email" name="email" placeholder="Enter Email Address" required>
             </td>
            </tr>
            <tr>
                <td><label>Password:</label></td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Login"></td>
            </tr>
            <tr>
                <td colspan="2"><p>don't have an account? <a href="signup.jsp">click here</a></p></td>
            </tr>
        </table>
    </form>
</body>
</html>