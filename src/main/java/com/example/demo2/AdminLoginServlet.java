package com.example.demo2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "adminLoginServlet", urlPatterns = "/adminLogin-servlet")
public class AdminLoginServlet extends HttpServlet {
    private void setContexts(Connection con) throws SQLException {
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("con",con);
        servletContext.setAttribute("services",getServices(con));
//        servletContext.setAttribute("userName",username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai","root","Kittu@96");
            String un = req.getParameter("username");
            String p = req.getParameter("password");
            setContexts(con);
            PreparedStatement ps = con.prepareStatement("select uname from logins where uname=? and password=?");
            ps.setString(1,un);
            ps.setString(2,p);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                HttpSession session = req.getSession();
                session.setAttribute("adminName",un);
                resp.sendRedirect("adminpage.jsp");
            }
            else{
                resp.sendRedirect("adminLogin.html");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getServices(Connection con) throws SQLException {
        ArrayList<String> services = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select service from payments;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
           services.add(rs.getString(1));
        }
        return services;
    }
}
