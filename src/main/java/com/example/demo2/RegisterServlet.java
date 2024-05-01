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
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "registerServlet", urlPatterns = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            PrintWriter out = resp.getWriter();
            ServletContext sc = getServletContext();
            Connection con = (Connection) sc.getAttribute("con");
            String un = req.getParameter("username");
            String p = req.getParameter("password");
            String phoneNumber = req.getParameter("phone");
            String email = req.getParameter("email");
//            String phoneNumber = req.getParameter("phone");
            String flat = req.getParameter("flat");
            PreparedStatement ps = con.prepareStatement("insert into logins values(?,?)");
            PreparedStatement ps1 = con.prepareStatement("insert into familytrail values (?,?,?,?,?)");
            con.prepareStatement("insert into financetrail values (0,0,0,0,?,'2024-03-10',0)").setString(1,un);
            ps.setString(1,un);
            ps.setString(2,p);
            ps1.setString(1,phoneNumber);
            ps1.setString(2,email);
            ps1.setBoolean(3,false);
            ps1.setString(4,un);
            ps1.setString(5,flat);
            int i = ps.executeUpdate();
            int j = ps1.executeUpdate();
            if (i<=0 || j<=0){
                out.println("Registration Unsuccessful. Try again!!");
            }
            else{
                resp.sendRedirect("adminpage.jsp");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
