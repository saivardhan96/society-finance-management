<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 31-03-2024
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Prevent caching so that it reloads again when I visit...
    if(session.getAttribute("adminName")==null) response.sendRedirect("index.jsp");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="adminactivity.css">
    <style>
        .neumorphic-button{
            width: 220px;
            height: 120px;
        }
    </style>
    <title>Payments</title>
</head>
<body>
<div class="heading">
    <h1>Krishna Meadows</h1>
</div>
<div class="buttons">
    <form action="AdminPayments-Servlet" method="post">
        <button class="neumorphic-button" type="submit"><i class="fas fa-money-check-alt"></i>
            <span>Payment Requests</span></button>
    </form>
    <button class="neumorphic-button" type="button" onclick="displayPage()"><i class="fas fa-coins"></i> <span>Set Payments</span></button>
<%--    <button class="neumorphic-button" type="button" ><i class="fas fa-coins"></i> <span>Back</span></button>--%>
</div>
<script>

    function displayPage(){
        alert("clicked");
         window.location.href='setPayments.jsp';
    }

    function takeMeaBack() {

    }
</script>
</body>

</html>
