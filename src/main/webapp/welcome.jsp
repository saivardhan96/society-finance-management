<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="User.LoginServlet" %><%--@elvariable id="userName" type="java.lang.String"--%>
<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 28-03-2024
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    // asking the page not to store cache.
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // for proxy servers
    if(session.getAttribute("userName")==null) response.sendRedirect("login.jsp");
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="UserActivity.css">
    <style>
        .neumorphic-button {
            text-decoration: none;
        }
    </style>

</head>
<body>
<div class="heading">
    <h1>Krishna Society</h1>
</div>
<div class="buttons">
    <button class="neumorphic-button" type="button" onclick="userPayments()"><i class="far fa-credit-card"></i><span>Payment</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-coins"></i><span>Community Expenditures</span></button>
    <button class="neumorphic-button" type="button"><i class="far fa-file-alt"></i><span>Community Report</span></button>
    <button class="neumorphic-button" type="button" onclick="logout()"><i class="fas fa-sign-out-alt"></i><span>Logout</span></button>

</div>
<script>
    function userPayments(){
        window.location.href="userPayment.jsp";
    }
    function logout(){
        window.location.href = "Logout-Servlet";
    }
</script>
</body>
</html>



