<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 11-08-2024
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<String> comServices = (ArrayList<String>) request.getAttribute("comService");
    ArrayList<String> comDate = (ArrayList<String>) request.getAttribute("comDate");
    ArrayList<Integer> comAmount = (ArrayList<Integer>) request.getAttribute("comAmount");

%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Community Expenditures</title>
    <link rel="stylesheet" href="table.css">
</head>
<body>
<h1>Community Expenditure</h1>

<table id="expenditureTable">
    <thead>
    <tr>
        <th>Purpose</th>
        <th>Amount</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody id="expenditureTableBody">
    <%
        for(int i=0;i<comServices.size();i++){
    %>
    <tr>

        <td> <%=comServices.get(i)%></td>
        <td> <%=comAmount.get(i)%></td>
        <td> <%=comDate.get(i)%></td>
    </tr>
    <% } %>
    </tbody>
</table>
<input type="submit" value="Okay" onclick="takeMeBack()">


<script>
    function takeMeBack() {
        window.history.replaceState(null, '', document.referrer); //replaces the history with previous page
        window.history.back(); // takes back to previous page

    }
</script>
</body>
</html>
