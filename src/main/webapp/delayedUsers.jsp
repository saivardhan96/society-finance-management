<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: saivardhan
  Date: 30-03-2024
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("adminName")==null) response.sendRedirect("index.jsp");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delayed Users</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 20px;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #dddddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .accept-button {
            padding: 5px 10px;
            background-color: #4caf50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .accept-button:hover {
            background-color: #45a049;
        }
        input[type="submit"] {
            display: block;
            margin: auto;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            text-transform: uppercase;

        }
    </style>
</head>
<body>

<%
    ServletContext sc = session.getServletContext();
    ArrayList<String> name = (ArrayList<String>) sc.getAttribute("delayedNames");
    ArrayList<String> phone = (ArrayList<String>) sc.getAttribute("delayedPhone");
    ArrayList<String> flat = (ArrayList<String>) sc.getAttribute("delayedFlat");
%>
<h1>Delayed Details</h1>

<table id="paymentTable">
    <thead>
    <tr>
        <th>Name</th>
        <th>Phone</th>
        <th>Flat</th>
    </tr>
    </thead>
    <tbody id="paymentTableBody">
    <%
        for(int i=0;i<name.size();i++){
    %>
    <tr>

        <td> <%=name.get(i)%></td>
        <td> <%=phone.get(i)%></td>
        <td> <%=flat.get(i)%></td>
    </tr>
    <% } %>
    </tbody>
</table>
<input type="submit" value="Back" onclick="takeMeBack()">


<script>
    function takeMeBack() {
        location.replace("/adminpage.jsp");
    }
</script>

</body>
</html>