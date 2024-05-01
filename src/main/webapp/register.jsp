<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 28-03-2024
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<jsp:include page="RegisterUser.html" />
</body>
</html>


<%--
<form action="register-servlet" method="post">

    <div>
        <label for="uname">User Name: </label>
        <input type="text" id="uname" name="uname" placeholder="User Name" required>
    </div>

    <div>
        <label for="pass">Password: </label>
        <input type="password" id="pass" name="pass" placeholder="New Password" required>
    </div>

    <div>
        <label for="pass2">Re-Enter Password: </label>
        <input type="password" id="pass2" name="pass2" placeholder="Re-Enter Password" required
               onchange="check()" >
    </div>
    <p id = "verif"></p>
    <div>
        <input type="Submit" value="Register" id="registerBtn" >
    </div>

    <script>
        function check(){
            let first = document.getElementById("pass");
            let second = document.getElementById("pass2")
            if (first===second){
                document.getElementById("registerBtn").classList.add("hidden ");
            }
            else{
                document.getElementById("verif").innerHTML = "Both passwords aren't matching..."
            }
        }
    </script>

</form>--%>