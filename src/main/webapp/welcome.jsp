<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %><%--@elvariable id="userName" type="java.lang.String"--%>
<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 28-03-2024
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome!!!</title>

</head>
<body>
<%
    if(session.getAttribute("username")==null) response.sendRedirect("login.jsp");
%>
Hey, ${userName}. Welcome to this page!!


</body>
</html>
