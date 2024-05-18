package com.example.demo2;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "updateexpenditureservlet" , urlPatterns = "/UpdateExpenditure-Servlet")
public class ExpenditureUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        ArrayList<String> services = (ArrayList<String>) sc.getAttribute("services");
        String service = req.getParameter("service");
        int amount = Integer.parseInt(req.getParameter("amount"));
        Connection con = (Connection) sc.getAttribute("con");
        try{
            PreparedStatement servicePs = con.prepareStatement("insert into payments values (?,?,0)");
            servicePs.setString(1,service);
            servicePs.setInt(2,amount);
            int serviceRs = servicePs.executeUpdate();
            int finRs = con.prepareStatement("update financetrail set reqStatus = concat(reqStatus,'0')").executeUpdate();
            PreparedStatement EUps = con.prepareStatement("insert into history values ('Admin',CURRENT_TIMESTAMP,?,?)");
            String serviceNo = String.valueOf(services.size());
            EUps.setString(1,serviceNo);
            EUps.setInt(2,amount);
            int EUrs = EUps.executeUpdate();
            if(serviceRs > 0 && EUrs > 0 && finRs > 0) resp.sendRedirect("adminpage.jsp");
            else resp.sendRedirect("updateExpenditure.jsp");
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
