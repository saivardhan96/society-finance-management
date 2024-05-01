<%--
  Created by IntelliJ IDEA.
  User: saiva AKA Saivardhan Ponduru
  Date: 29-03-2024
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // for proxy servers
    if(session.getAttribute("adminName")==null) response.sendRedirect("index.jsp");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> <!-- Linking Font Awesome -->
    <link rel="stylesheet" href="adminactivity.css">
</head>
<body>
<div class="heading">
    <h1>Krishna Meadows</h1>
</div>
<div class="buttons">
    <button class="neumorphic-button" type="button"><i class="fas fa-user" onclick="registerUser()"></i><span>Register User</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-sync"></i><span>Update Expenditure</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-chart-bar" onclick="delayedUsers()"></i><span>Delayed Reports</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-receipt" onclick="adminPayments()"></i><span>Payments</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-sign-out-alt" onclick="adminLogout()"></i><span>Logout</span></button>

</div>

<script>
    function adminLogout() {
        window.location.replace("Logout-Servlet");

    }
    function registerUser(){
        window.location.href="register.jsp";
    }
    function delayedUsers(){

        window.location.href="DelayedDetails-Servlet";
    }
    function adminPayments(){
        window.location.href ="adminPayments.jsp";
    }
</script>


</body>
</html>

