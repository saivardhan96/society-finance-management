<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 28-03-2024
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("adminName")==null) response.sendRedirect("index.jsp");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // for proxy servers
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="RegisterUser.css">
</head>
<body>
<h1>User Registration</h1>
<form id="registrationForm" action="register-servlet" method="post">
    <div class="form-group">
        <label for="flat">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div class="form-group">
        <label for="phone">Phone Number:</label>
        <input type="tel" id="phone" name="phone" required>
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <div class="form-group">
        <label for="flat">Flat Number:</label>
        <input type="text" id="flat" name="flat" required>
    </div>
    <button type="submit" >Register</button>
</form>

<%
    String msg = request.getParameter("warning");
    if(msg!=null){
%>
<h2 style="color: red;margin-top: 10px; text-align: center;"><%=msg%></h2>
<%}%>
</body>
</html>
<%--
Follow ups:
Add password verification and OTP verification field.
--%>