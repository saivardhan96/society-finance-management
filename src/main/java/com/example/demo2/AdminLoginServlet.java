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
        ArrayList<ArrayList<String>> ans = delyaedDetails(con);
        if(!ans.isEmpty()) {
            servletContext.setAttribute("delayedNames", ans.get(0));
            servletContext.setAttribute("delayedPhone", ans.get(1));
            servletContext.setAttribute("delayedFlat", ans.get(2));
        }
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

    // delayed users -- details retrieving
    private ArrayList<ArrayList<String>> delyaedDetails(Connection con) throws SQLException{
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> phone = new ArrayList<>();
        ArrayList<String> flat = new ArrayList<>();
        PreparedStatement delayedPs = con.prepareStatement("select familytrail.name , familytrail.phone_number, familytrail.flat from familytrail join financetrail on familytrail.uname = financetrail.uname where financetrail.last_paid < '2024-05-10'");
        ResultSet delayrs = delayedPs.executeQuery();
        while(delayrs.next()){
            name.add(delayrs.getString("name"));
            phone.add(delayrs.getString("phone_number"));
            flat.add(delayrs.getString("flat"));
        }
        return new ArrayList<>(){{
            add(name);
            add(phone);
            add(flat);
        }};
    }
}
