package com.example.demo2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "newPaymentServlet",urlPatterns = "/NewPayment-Servlet")
public class NewPaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        Connection con = (Connection) servletContext.getAttribute("con");
        String un = (String) servletContext.getAttribute("userName");
        int dueAmount = 0;
        try {
            PreparedStatement ps = con.prepareStatement("select due_amount from financetrail where uname =?;");
            ps.setString(1,un);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) dueAmount=rs.getInt(1);
            System.out.println("Due amount: "+dueAmount); // printing
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> serviceList = (ArrayList<String>) getServletContext().getAttribute("serviceList");
        ArrayList<Integer> amountList = (ArrayList<Integer>) getServletContext().getAttribute("amountList");
//        System.out.println(serviceList);
        if(!serviceList.isEmpty() && dueAmount>0){
            req.setAttribute("serviceList",serviceList);
            req.setAttribute("amountList",amountList);
            RequestDispatcher rd = req.getRequestDispatcher("requests.jsp");
            rd.forward(req,resp);
        }
        else{
            resp.setContentType("text/html");
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>No Pending Payments.</title>\n" +
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
                    "    <h1>No Pending Payments.</h1>\n" +
                    "    <p><a href = 'welcome.jsp' >Go Back</a></p>\n" +
                    "</body>\n" +
                    "</html>");
        }
    }
}
