package com.example.demo2;

import User.Payment;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "adminLoginServlet", urlPatterns = "/adminLogin-servlet")
public class AdminLoginServlet extends HttpServlet {
    private void setContexts(String username, Connection con){
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("con",con);
        servletContext.setAttribute("userName",username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai","root","Kittu@96");
            String un = req.getParameter("username");
            String p = req.getParameter("password");
            setContexts(un,con);
            PreparedStatement ps = con.prepareStatement("select uname from logins where uname=? and password=?");
            ps.setString(1,un);
            ps.setString(2,p);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                RequestDispatcher rd = req.getRequestDispatcher("adminpage.jsp");
                rd.forward(req,resp);
            }
            else{
                resp.setContentType("text/html");
                out.println("<font color=red size=30> Login Failed!<br>");
                out.println("<a href=login.jsp> TRY AGAIN");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
