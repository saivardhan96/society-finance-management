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

    ArrayList<String> usernames = (ArrayList<String>) request.getAttribute("usernames");
    ArrayList<String> amount = (ArrayList<String>) request.getAttribute("amounts");
    StringBuilder acceptedUsernames = new StringBuilder();
%>
<h1>Payment Requests</h1>
<form action="PaymentRequests-Admin" method="post" >
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
            <td><button class="accept-button" onclick=<%acceptedUsernames.append(usernames.get(i)).append(" ");%>
                "acceptRequest('<%= usernames.get(i) %>', this)" >
                Accept</button></td>
        </tr>
        <input type="hidden" id="acceptedUsernames" name="acceptedUsernames" value=<%= acceptedUsernames.toString() %>>
        <% } %>
        </tbody>
    </table>
    <input type="hidden" id="acceptedAmounts" name="acceptedNames" value="" >
    <input type="submit" value="Done" onclick="showAlert()">
</form>


<script>
    let jj = "";
    function acceptRequest(username, button) {
        document.getElementById("acceptedAmounts").ariaDisabled=false;
        alert("Payment request from "+username+" accepted!");
        jj+=username+" ";
        document.getElementById("acceptedUsernames").value=jj;
        console.log(jj);
        button.textContent = "Accepted";
        button.disabled = true;
    }
    function showAlert() {
        alert("Done!!!")
        history.replaceState(null,document.title,"/adminpage.jsp")

    }

</script>
</body>
</html>