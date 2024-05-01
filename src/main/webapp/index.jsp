<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Krishna Meadows</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-image: url('https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=1000&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8aW50ZXJpb3IlMjBkZXNpZ258ZW58MHx8MHx8fDA%3D');
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
      overflow: hidden;
      height: 100vh; /* Set height to fill entire viewport */
    }

    .welcome-button {
      font-family: "Open Sans", sans-serif;
      font-weight: bold;
      font-size: 16px;
      letter-spacing: 2px;
      text-decoration: none;
      text-transform: uppercase;
      color: #000;
      cursor: pointer;
      border: 3px solid;
      padding: 0.25em 0.5em;
      box-shadow: 1px 1px 0px 0px, 2px 2px 0px 0px, 3px 3px 0px 0px, 4px 4px 0px 0px, 5px 5px 0px 0px;
      position: relative;
      user-select: none;
      -webkit-user-select: none;
      touch-action: manipulation;
      background: linear-gradient(to right, #ff7e5f, #feb47b); /* Linear gradient background */
      transition: background 0.3s, transform 0.2s, box-shadow 0.2s, color 0.3s; /* Added color transition */
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      margin: 10px; /* Added margin */
      margin-top: 50px;
      padding: 15px;
    }

    .welcome-button:hover {
      background: linear-gradient(to right, #fe6b55, #fdab81); /* Change gradient colors on hover */
      transform: translateY(-3px) scale(1.02); /* Move button up slightly and scale */
      box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Adjust shadow on hover */
      color:purple; /* Change text color on hover */
      animation: blast 0.5s ease forwards; /* Apply blast animation */
    }

    @keyframes blast {
      0% {
        transform: scale(1);
      }
      50% {
        transform: scale(1.2);
      }
      100% {
        transform: scale(1);
      }
    }

    .welcome-button:active {
      transform: translateY(0) scale(0.98); /* Scale button down slightly on click */
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Adjust shadow on click */
    }

    .container {
      width: 100%;
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
      text-align: center;
    }

    h1 {
      font-size: 3em;
      margin-bottom: 20px;
      color: #fff; /* Change text color */
    }

    p {
      font-size: 1.2em;
      color: #f0f0f0; /* Change text color */
      margin-bottom: 40px;
    }

    .quotation {
      font-style: italic;
      color: #060606; /* Change quotation text color */
      font-weight: bold;
    }

  </style>
</head>
<body>
<div class="container">
  <h1>Welcome to Krishna Meadows</h1>
  <p>Experience the comfort of modern living in a serene environment.</p>
  <p class="quotation">"Home is where love resides, memories are created, friends always belong, and laughter never ends."</p>
  <button class="welcome-button" onclick="admin()">Admin Login</button>
  <button class="welcome-button" onclick="user()">User Login</button> <!-- Changed button text -->
</div>
<script>
  function user(){
    window.location.href="login.jsp";
  }
  function admin(){
    window.location.href="AdminLogin.html";
  }
</script>
</body>
</html>