package com.example.demo2;

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

@WebServlet(name = "setPaymentsServlet",urlPatterns = "/SetPayments-Servlet")
public class SetPaymentsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            PrintWriter out = resp.getWriter();
            ServletContext sc = getServletContext();
            Connection con = (Connection) sc.getAttribute("con");
            String service = req.getParameter("service");
            String amount = req.getParameter("amount");
            PreparedStatement ps = con.prepareStatement("insert into payments values(?,?)");
            ps.setString(1,service);
            ps.setString(2,amount);
            PreparedStatement ps1 = con.prepareStatement("update financetrail set due_amount = due_amount+? ");
            ps1.setInt(1,Integer.parseInt(amount));
            int rs1 = ps1.executeUpdate();

            int i = ps.executeUpdate();
            if(i>0 && rs1>0){
                resp.sendRedirect("adminpage.jsp");
            }
            else out.println("unSuccessfully set!!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
