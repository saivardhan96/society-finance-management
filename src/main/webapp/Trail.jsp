<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 02-04-2024
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // asking the page not to store cache.
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // for proxy servers
    if(session.getAttribute("userName")==null) response.sendRedirect("login.jsp");
%>
<html>
<head>
    <title>Requests Trail</title>
    <link rel="stylesheet" href="paymentRequest.css">
</head>
<h1>Payment Request</h1>
<body>

<%
    int tableRows = 0;
    ArrayList<String> services = (ArrayList<String>) session.getServletContext().getAttribute("serviceList");
//    System.out.println("SERVICES ACTUALLY: "+services);
    ArrayList<Integer> amounts = (ArrayList<Integer>) session.getServletContext().getAttribute("amountList");
    StringBuilder reqLis =  new StringBuilder(session.getServletContext().getAttribute("reqList").toString());
    ArrayList<String> reqItems = (ArrayList<String>) session.getServletContext().getAttribute("requestedItems");
    System.out.println("Requested Items are: "+ reqItems);
%>
<body>

<form action="TrailRequest-Servlet" method="post" > <%-- removed  to set onclick function ?--%>
    <table border="0" id="requestsTable">
        <thead>
        <tr>
            <th></th>
            <th>Service</th>
            <th>Amount</th>
        </tr>
        </thead>
        <tbody>
        <% for (int i=0;i< services.size();i++) { %>
            <%  char c = reqLis.charAt(i);
            if(reqItems!=null){
                if(c=='1' && !reqItems.contains(String.valueOf(i))){
                tableRows++;
                String rowId = "row"+i;
            %>
                <tr id=<%=rowId%> >
                    <% String serviceId = "service"+i;%>
                    <% String amountId = "amount"+i;%>
                    <td><label><input type="checkbox" class="checkbox" id=<%=i%>></label></td>
                    <td id=<%=serviceId%> > <%= services.get(i) %> </td>
                    <td id=<%=amountId%> ><%=amounts.get(i)%></td>
                </tr>
                <%}
                }
            else{
                if(c =='1'){
                    tableRows++;
                    String rowId = "row"+i;
                %>

        <tr id=<%=rowId%> >
            <% String serviceId = "service"+i;%>
            <% String amountId = "amount"+i;%>
            <td><label><input type="checkbox" class="checkbox" id=<%=i%>></label></td>
            <td id=<%=serviceId%> > <%= services.get(i) %> </td>
            <td id=<%=amountId%> ><%=amounts.get(i)%></td>
        </tr>

        <%  }
        }
            }%>
        </tbody>
    </table>
    <input type="submit" value="send Request" id="sendRequestBtn" disabled onclick="showAlert()" >
    <input type="hidden" id="hiddenField" name="totalAmountHidden">
    <input type="hidden" id="rows" name="rows" value=<%=tableRows%>>
    <input type="hidden" id="paidItems" name="paidItems" value=>

</form>

<script>
    let sumAmount = 0;
    let paidItems = "";
    const table = document.getElementById("requestsTable");
    const sendButton =document.getElementById("sendRequestBtn");

    const rows = Number(document.getElementById('rows').value);
    // console.log(rows);
    const checkboxes = document.getElementsByClassName('checkbox');
    for (let i = 1; i<=rows; i++) {

        if (table.rows[i].cells[2].textContent!=null) {
            checkboxes[i-1].addEventListener('change', function (qualifiedName, value) {
                let amount = table.rows[i].cells[2].textContent;
                // console.log("MOUNT: "+amount)
                let items = checkboxes[i-1].id;
                if (this.checked) {
                    sendButton.removeAttribute("disabled");
                    sumAmount += Number(amount);
                    // console.log("checked" + amount); //log
                    paidItems += String(items);
                    paidItems+=",";
                    document.getElementById("sendRequestBtn").disabled=false//removing the unchecked item from paiditems
                }
                else {
                    sumAmount -= Number(amount);
                    if(sumAmount===0) sendButton.setAttribute("disabled", value);
                    // console.log("unchecked: " + amount);
                    let oldItems = document.getElementById("paidItems").value.toString();
                    const startInd = oldItems.indexOf(items);
                    const endInd = startInd + items.length + 1
                    paidItems = oldItems.slice(0, startInd) + oldItems.slice(endInd);
                    // console.log("Unchecked paid items: " + paidItems);
                }
                document.getElementById("hiddenField").value = sumAmount;
                document.getElementById("paidItems").value = paidItems;
                console.log(paidItems);
                console.log(sumAmount);
            })
        }
    }
    function showAlert() {
        alert("Request sent successfully!!!");
        history.replaceState(null,document.title,"/welcome.jsp"); // not storing this page in history stack.
    }


</script>
</body>
</html>