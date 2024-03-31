<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %><%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 30-03-2024
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Requests</title>
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
    </style>
</head>
<body>

<%
    ArrayList<String> usernames = (ArrayList<String>) request.getAttribute("usernamesList");
    ArrayList<String> amount = (ArrayList<String>) request.getAttribute("amountList");
%>
<h1>Payment Requests</h1>
<table id="paymentTable">
    <thead>
    <tr>
        <th>Username</th>
        <th>Amount</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody id="paymentTableBody">

    <%
        for(int i=0;i<usernames.size();i++){%>
            <tr>
                <td> <%=usernames.get(i)%></td>
                <td> <%=amount.get(i)%></td>
                <td><button class="accept-button" onclick=
                    <%
                    Connection con = (Connection) request.getAttribute("con");
                    PreparedStatement ps = con.prepareStatement("update financetrail set request='approved' where uname=?;");
                    ps.setString(1,usernames.get(i));
                    int j = ps.executeUpdate();
                    if(j>0) System.out.println("This is checked!!!!");
                    else System.out.println("cot checked");
                    %>"acceptRequest('<%= usernames.get(i) %>', this)">
                    Accept</button></td>
            </tr>
    <% } %>

    </tbody>
</table>


<script>
    function acceptRequest(username, button) {
        alert("Payment request from "+username+" accepted!");
        button.textContent = "Accepted";
        button.disabled = true;
    }

</script>
</body>
</html>