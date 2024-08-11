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
import java.util.ArrayList;

@WebServlet(name = "communityExpenditure", urlPatterns = {"/expenditure*","/communityExpenditures"})
public class CommunityExpenditureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        Connection con = (Connection) servletContext.getAttribute("con");
        ArrayList<String> comService = new ArrayList<>();
        ArrayList<Integer> comAmount = new ArrayList<>();
        ArrayList<String> comDate = new ArrayList<>();
        try{
            PreparedStatement cePS = con.prepareStatement("select services as purpose, substr(paidDate,1,10) as Date, amount from history where uname ='Admin' ");
            ResultSet ceRs = cePS.executeQuery();
            while (ceRs.next()){
                comService.add(ceRs.getString(1));
                comDate.add(ceRs.getString(2));
                comAmount.add(Integer.valueOf(ceRs.getString(3)));
            }
            req.setAttribute("comService",comService);
            req.setAttribute("comDate",comDate);
            req.setAttribute("comAmount",comAmount);
            req.getRequestDispatcher("/communityExpenditures.jsp").forward(req,resp);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
