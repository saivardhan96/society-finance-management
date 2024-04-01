package com.example.demo2;

import User.Payment;
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
import java.util.Objects;

import static java.lang.System.console;
import static java.lang.System.out;

@WebServlet(name = "loginServlet", urlPatterns = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hey!!");
        resp.getWriter().println("This is next line");
    }


    private void setContexts(String username, Connection con) throws SQLException {
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("con",con);
        servletContext.setAttribute("userName",username);
        servletContext.setAttribute("serviceList",getServices(con));
        servletContext.setAttribute("amountList",getamounts(con));
        servletContext.setAttribute("requestStatus",getReqStatus(con,username));
    }

    private ArrayList<String> getServices(Connection con) throws SQLException {
        PreparedStatement servicesPs = con.prepareStatement("select service from payments");
        ResultSet rs = servicesPs.executeQuery();
        ArrayList<String> servicesList = new ArrayList<>();
        while (rs.next()){
            servicesList.add(rs.getString(1));
        }
        return servicesList;
    }
    private ArrayList<Integer> getamounts(Connection con) throws SQLException {

        PreparedStatement amountPs = con.prepareStatement("select amount from payments");
        ResultSet amountRs = amountPs.executeQuery();
        ArrayList<Integer> amountList = new ArrayList<>();
        while (amountRs.next()){
            amountList.add(amountRs.getInt(1));
        }
        return amountList;
    }

    private String getReqStatus(Connection con,String un) throws SQLException {
        PreparedStatement reqPs = con.prepareStatement("select request from financetrail where uname=?");
        reqPs.setString(1,un);
        ResultSet reqResultSet = reqPs.executeQuery();
        String reqStat = "";
        if(reqResultSet.next()) reqStat+= reqResultSet.getString(1);
        return reqStat;
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
            PreparedStatement ps = con.prepareStatement("select password from logins where uname=?");
            ps.setString(1,un);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString(1);
                System.out.println("Password: "+password);
                if(Objects.equals(password, p)){
                    HttpSession session = req.getSession();
                    session.setAttribute("username",un);
                    resp.sendRedirect("welcome.jsp");
                }
                else{
                    req.setAttribute("Incorrect","Wrong Credentials");
                    req.getRequestDispatcher("login.jsp").forward(req,resp);
                }
            }
            else{
                req.setAttribute("Incorrect","Wrong Credentials");
                req.getRequestDispatcher("login.jsp").forward(req,resp);;
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
