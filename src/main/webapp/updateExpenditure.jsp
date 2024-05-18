<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 15-05-2024
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Expenditure</title>
    <link rel="stylesheet" href="RegisterUser.css">
</head>
<body>
<h1>Krishna Society</h1>
<form action="UpdateExpenditure-Servlet" method="post">
    <div class="form-group">
        <label for="service">Service:</label>
        <input type="text" id="service" name="service" required>
    </div>

    <div class="form-group">
        <label for="amount">Amount:</label>
        <input type="text" id="amount" name="amount" required>
    </div>
    <button type="submit" id="subbtn" onclick="showAlert()">Done</button>
</form>
<script>
    function showAlert(){
        alert("New Expenditure updated successfully.")
    }
</script>
</body>
</html>
