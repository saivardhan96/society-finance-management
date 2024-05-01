<%--
  Created by IntelliJ IDEA.
  User: saiva
  Date: 29-03-2024
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // for proxy servers
    if(session.getAttribute("userName")==null) response.sendRedirect("login.jsp");
    // will get payment status of the user...

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payments</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="UserActivity.css">
    <style>
        /* Styling for the overlay */
        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.5); /* Semi-transparent white background */
            backdrop-filter: blur(10px); /* Increased blur effect */
            z-index: 999; /* Ensure it appears above other content */
            display: none; /* Initially hidden */
        }

        /* Styling for the text in the overlay */
        .overlay-text {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: #000000; /* Text color */
            font-size: 24px;
            text-align: center;
            padding: 20px;
            font-weight: bold;
            /* background-color: rgba(255, 255, 255, 0.9); /* Semi-transparent white background */
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(6, 6, 211, 0.1); /* Shadow effect */
        }

        /* Styling for the close button */
        .close-button {
            position: absolute;
            top: 10px;
            right: 10px;
            color: #000000; /* Close button color */
            font-size: 30px;
            cursor: pointer;
        }
        .neumorphic-button{
            width: 220px;
            height: 120px;
        }
    </style>
</head>
<body>
<div class="heading">
    <h1>Krishna Meadows</h1>
</div>
<div class="buttons">
    <form action="TrialPayment-Servlet" method="post">
        <button class="neumorphic-button" type="submit"><i class="far fa-credit-card"></i><span>New Payment</span></button>
    </form>
    <button class="neumorphic-button" type="button" onclick="showOverlayWithText()"><i class="fas fa-info-circle"></i><span>Current Status</span></button>
    <button class="neumorphic-button" type="button"><i class="fas fa-history"></i><span>Previous History</span></button>

</div>
<!-- Overlay -->
<div class="overlay" id="overlay">
    <div class="overlay-text" id="overlayText">
        <span onclick="hideOverlay()" class="close-button">&times;</span>
        <p id="overlayMessage"></p>
    </div>
</div>

<script>
    function showOverlay() {
        document.getElementById('overlay').style.display = 'block';
        // You can add additional logic here if needed
    }

    function showOverlayWithText() {
        // Show the overlay
        document.getElementById('overlay').style.display = 'block';
        document.getElementById('overlayMessage').innerText = 'your request was ${requestStatus}';
    }

    function hideOverlay() {
        document.getElementById('overlay').style.display = 'none';
    }
</script>
</body>
</html>
