<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 28-03-2024
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Garamond&display=swap">


    <link rel="stylesheet" href="UserLoginfin.css">
</head>
<body>
<div class="heading">
    <h1>Krishna Meadows</h1>
</div>
<div class="form">
    <form action="login-servlet" method="post" >
        <div>
            <h2>Login</h2>
            <label for="username">UserName:</label>
            <input type="text" id="username" name="username" class="input" placeholder="UserName" required>
        </div>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" class="input" placeholder="Password" required>
        <br>
        <button type="submit">Login</button><br>
        <div><a href="AdminLogin.html">Admin Login?</a></div>
    </form>
</div>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
%>


</body>
</html>