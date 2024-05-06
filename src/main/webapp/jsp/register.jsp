<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register User</title>
</head>
<body>
<h2>Register New User</h2>
<form action="RegisterServlet" method="POST">
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password" required>
    </div>
    <div>
        <label>First Name:</label>
        <input type="text" name="firstName">
    </div>
    <div>
        <label>Last Name:</label>
        <input type="text" name="lastName">
    </div>
    <button type="submit">Register</button>
</form>
</body>
</html>
