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
    ArrayList<Integer> amounts = (ArrayList<Integer>) session.getServletContext().getAttribute("amountList");
    StringBuilder reqLis =  new StringBuilder(session.getServletContext().getAttribute("reqList").toString());
    ArrayList<Character> reqItems = (ArrayList<Character>) session.getServletContext().getAttribute("requestedItems");
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
        <%-- Iterate over the values and generate table rows --%>
        <% for (int i=0;i< services.size();i++) { %>
            <%  char c = reqLis.charAt(i);
            if(reqItems!=null){
                if(c=='1' && !reqItems.contains(c)){
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
                if(c=='1'){
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
        // console.log("Checkbox: "+checkboxes[i-1].id)
        // console.log("Amount: "+table.rows[i].cells[2].textContent)

        if (table.rows[i].cells[2].textContent!=null) {
            checkboxes[i-1].addEventListener('change', function (qualifiedName, value) {
                // console.log(checkboxes[i-1].id)
                let amount = table.rows[i].cells[2].textContent;
                // console.log("MOUNT: "+amount)
                let items = checkboxes[i-1].id;
                if (this.checked) {
                    sendButton.removeAttribute("disabled");
                    sumAmount += Number(amount);
                    // console.log("checked" + amount); //log
                    // console.log("checked" + items); //log
                    paidItems += String(items);
                    document.getElementById("sendRequestBtn").disabled=false//removing the unchecked item from paiditems
                    // console.log(paidItems);
                }
                else {
                    sumAmount -= Number(amount);
                    if(sumAmount===0) sendButton.setAttribute("disabled", value);
                    // console.log("unchecked: " + amount);
                    let oldItems = document.getElementById("paidItems").value.toString();
                    const ind = oldItems.indexOf(items);
                    paidItems = oldItems.slice(0, ind) + oldItems.slice(ind + 1);
                    // console.log("Unchecked paid items: " + paidItems);
                }
                document.getElementById("hiddenField").value = sumAmount;
                document.getElementById("paidItems").value = paidItems;
                 // added jus now
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


<%--        const table = document.getElementById('myTable').getElementsByTagName('tbody')[0]; // to get table
        for( let i=0;i<<%=totalRows%>;i++){
            var row = table.rows[i];
            var checkBox = row.cells[0].querySelector('input[type="checkbox"]');
            if(checkBox.checked){
                var retrAmount = row.cells[2].textContent;
                sumAmount+=Number(retrAmount);
                const cbId = checkBox.id;
                paidItems+=String(cbId);
            }

        }--%>
<%--
/*    // Attach event listener to each checkbox

let i=0;

let totalAmount = 0;
checkboxes.forEach(function (checkbox) {
checkbox.addEventListener('change', function () {
let query = 'amount'+i.toString();
const retramount = document.getElementById(query).textContent;


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
console.log(totalAmount);*/
--%>
<%--
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // for proxy servers %>--%>
