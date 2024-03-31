<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 30-03-2024
  Time: 02:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dynamically Create Table</title>
    <link rel="stylesheet" href="paymentRequest.css">
</head>
<h1>Payment Request</h1>
<body>


    <% ArrayList<String> services = (ArrayList<String>) request.getAttribute("serviceList");
        ArrayList<Integer> amounts = (ArrayList<Integer>) request.getAttribute("amountList");
    %>

<form action="TrailRequest-Servlet" method="post">
    <table border="0" id="requestsTable">
        <thead>
        <tr>
            <th></th>
            <th>Service</th>
            <th>Amount</th>
        </tr>
        </thead>
        <tbody>
        <%-- Iterate over the values and generate table rows --%>
        <% for (int i=0;i< services.size();i++) { %>
        <% String rowId = "row"+i;%>
        <tr id=<%=rowId%>>
            <% String serviceId = "service"+i;%>
            <% String amountId = "amount"+i;%>
            <td><label><input type="checkbox" class="checkbox"></label></td>
            <td id=<%=serviceId%> > <%= services.get(i) %> </td>
            <td id=<%=amountId%> ><%=amounts.get(i)%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <input type="submit" value="send Request" onclick="showAlert()">
    <input type="hidden" id="hiddenField" name="totalAmountHidden">
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>


</form>

    <script>
        // Get all checkboxes in the table
        const checkboxes = document.querySelectorAll('#requestsTable .checkbox');

        // Attach event listener to each checkbox
        let i=0;
        var totalAmount = 0;
        checkboxes.forEach(function (checkbox) {
            checkbox.addEventListener('change', function () {
                // Get the corresponding value from the third column (Column 3)
                // const row = this.parentNode.parentNode; // Get the parent row
                let query = 'amount'+i.toString();
                var retramount = document.getElementById(query).textContent; // Get the value from the third column


                // Store the value as needed (for example, log it to the console)
                if (this.checked) {
                    totalAmount+=Number(retramount);
                    console.log("totalAmount: "+totalAmount);
                    console.log("Checkbox checked, amount is:", retramount);
                } else {
                    totalAmount-=Number(retramount);
                    console.log("totalAmount: "+totalAmount);
                    console.log("Checkbox unchecked");
                }
                i++;
                document.getElementById("hiddenField").value = totalAmount;
            });
        });
        console.log(totalAmount);

        function showAlert() {
            alert("Request sent successfully!!!");
        }
    </script>





</body>
</html>


