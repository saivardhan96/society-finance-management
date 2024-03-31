package com.example.demo2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "adminpaymentsservlet", urlPatterns = "/AdminPayments-Servlet")
public class AdminPaymentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        Connection con = (Connection) servletContext.getAttribute("con");
        try {

            PreparedStatement ps = con.prepareStatement("select uname,reqAmount from financetrail where request='sent';");
            ResultSet rs = ps.executeQuery();
            ArrayList<String> usernames = new ArrayList<>();
            ArrayList<String> amount = new ArrayList<>();
            while (rs.next()){
                usernames.add(rs.getString("uname"));
                amount.add(rs.getString("reqAmount"));
            }
            if(!usernames.isEmpty()){
                req.setAttribute("usernamesList",usernames);
                req.setAttribute("amountList",amount);
                req.setAttribute("con",con);
                req.getRequestDispatcher("adminPaymentRequests.jsp").forward(req,resp);
            }
            else {
                resp.setContentType("text/html");
                out.println("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Pending Requests</title>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            text-align: center;\n" +
                        "            margin-top: 50px;\n" +
                        "        }\n" +
                        "\n" +
                        "        h1 {\n" +
                        "            color: #333;\n" +
                        "        }\n" +
                        "\n" +
                        "        a {\n" +
                        "            color: #007bff;\n" +
                        "            text-decoration: none;\n" +
                        "            font-size: 20px;\n" +
                        "            cursor: pointer;\n" +
                        "        }\n" +
                        "\n" +
                        "        a:hover {\n" +
                        "            text-decoration: underline;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>No Pending Requests</h1>\n" +
                        "    <p><a href = 'adminpage.jsp' >Go Back</a></p>\n" +
                        "</body>\n" +
                        "</html>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
