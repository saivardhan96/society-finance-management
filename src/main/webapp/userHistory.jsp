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
    if(session.getAttribute("userName")==null) response.sendRedirect("index.jsp");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment History</title>
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
    ArrayList<String> services = (ArrayList<String>) sc.getAttribute("historyServices");
    ArrayList<String> amount = (ArrayList<String>) sc.getAttribute("historyAmount");
    ArrayList<String> paidDate = (ArrayList<String>) sc.getAttribute("historyPaidDate");
    ArrayList<String> serviceList = (ArrayList<String>) sc.getAttribute("serviceList");
%>
<h1>Payment History</h1>

    <table id="paymentTable">
        <thead>
        <tr>
            <th>Service</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody id="paymentTableBody">
        <%
            for(int i=0;i<services.size();i++){
                StringBuilder service = new StringBuilder();
                String currService = services.get(i);
                int l = services.get(i).length();
                for(int j=0;j<l;j++){
                    service.append(serviceList.get(currService.charAt(j) - '0'));
                    if(j!=l-1) service.append(" | ");
                }
        %>
        <tr>

            <td> <%=service.toString()%></td>
            <td> <%=amount.get(i)%></td>
            <td> <%=paidDate.get(i).substring(0,11)%></td>
            <td> <%=paidDate.get(i).substring(12)%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <input type="submit" value="Back" onclick="takeMeBack()">


<script>
    function takeMeBack() {
        location.replace("/userPayment.jsp");
    }
</script>

</body>
</html>