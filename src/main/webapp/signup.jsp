<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Page</title>
</head>
<body>
    <form action="SignUpServlet" method="post">
        <table>
            <tr>
                <td><label>Email Id:</label></td>
                <td><input type="email" name="email" placeholder="Enter your email address" required>
             </td>
            </tr>
            <tr>
                <td><label>Password:</label></td>
                <td><input type="password" name="password" placeholder="Enter your Password" required></td>
            </tr>
            <tr>
                <td><label>Name:</label></td>
                <td><input type="text" name="name" placeholder="Enter your Name" required></td>
            </tr>
            <tr>
                <td><label>Date of Birth:</label></td>
                <td><input type="date" name="dateOfBirth" placeholder="Enter your Date of Birth" required></td>
            </tr>
            <tr>
                <td><label>Age:</label></td>
                <td><input type="text" name="age" placeholder="Enter your Age" required></td>
            </tr>
            <tr>
                <td><label>State:</label></td>
                <td><input type="text" name="state" placeholder="Enter your State" required></td>
            </tr>
            <tr>
                <td><label>City:</label></td>
                <td><input type="text" name="city" placeholder="Enter your City" required></td>
            </tr>
            <tr>
                <td><label>Phone Number:</label></td>
                <td><input type="text" name="phoneNumber" placeholder="Enter your Phone Number" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Register User"></td>
            </tr>
            <tr>
                <td colspan="2"><p>already have an account? <a href="login.jsp">click here</a></p></td>
            </tr>
        </table>
    </form>
</body>
</html>