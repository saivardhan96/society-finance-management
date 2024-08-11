<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 30-03-2024
  Time: 00:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if(session.getAttribute("adminName")==null) response.sendRedirect("index.jsp");%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payments</title>
    <link rel="stylesheet" href="RegisterUser.css">
</head>
<body>
<h1>Krishna Society</h1>
<form action="SetPayments-Servlet" method="post">
    <div class="form-group">
        <label for="service">Service:</label>
        <input type="text" id="service" name="service" required>
    </div>
    <div class="form-group">
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" required>
    </div>
    <button type="submit" id="subbtn" onclick="showAlert()">Done</button>
</form>
<script>
    function showAlert(){
        alert("New Payment requests set successfully.")
    }
</script>
</body>
</html>
