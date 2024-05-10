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
            String name = req.getParameter("name");
            String flat = req.getParameter("flat");
            PreparedStatement loginPS = con.prepareStatement("insert into logins values(?,?)");
            PreparedStatement familyPs = con.prepareStatement("insert into familytrail values (?,?,?,?,?)");
            PreparedStatement finPs = con.prepareStatement("insert into financetrail values (0,0,0,0,?,null,0)");
            finPs.setString(1,un);
            loginPS.setString(1,un);
            loginPS.setString(2,p);
            familyPs.setString(1,phoneNumber);
            familyPs.setString(2,email);
//            familyPs.setBoolean(3,false); // not there
            familyPs.setString(3,un);
            familyPs.setString(4,flat);
            familyPs.setString(5,name);
            int i = loginPS.executeUpdate();
            int j = familyPs.executeUpdate();
            int k = finPs.executeUpdate();
            if (i<=0 || j<=0 || k<=0) {
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
